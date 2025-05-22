package com.tourism.customizationplatform.service;

import com.tourism.customizationplatform.model.*;
import com.tourism.customizationplatform.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Value("${file.paths.users}")
    private String usersFilePath;

    // Create
    public void registerUser(User user) throws IOException {
        FileUtil.writeToFile(usersFilePath, user.toFileFormat(), true);
    }

    // Read
    public User findUserById(String id) throws IOException {
        List<String> lines = FileUtil.readFromFile(usersFilePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(id)) {
                return createUserFromData(parts);
            }
        }
        return null;
    }

    public User findUserByUsername(String username) throws IOException {
        List<String> lines = FileUtil.readFromFile(usersFilePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[1].equals(username)) {
                return createUserFromData(parts);
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        List<String> lines = FileUtil.readFromFile(usersFilePath);
        for (String line : lines) {
            String[] parts = line.split(",");
            users.add(createUserFromData(parts));
        }
        return users;
    }


    // Update
    public void updateUser(User updatedUser) throws IOException {
        List<String> lines = FileUtil.readFromFile(usersFilePath);
        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts[0].equals(updatedUser.getId().toString())) {
                updatedLines.add(updatedUser.toFileFormat());
            } else {
                updatedLines.add(line);
            }
        }
        FileUtil.overwriteFile(usersFilePath, updatedLines);
    }

    // Delete
    public void deleteUser(String id) throws IOException {
        List<String> lines = FileUtil.readFromFile(usersFilePath);
        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (!parts[0].equals(id)) {
                updatedLines.add(line);
            }
        }
        FileUtil.overwriteFile(usersFilePath, updatedLines);
    }

    // Login (Polymorphism in user type handling)
    public User login(String username, String password) throws IOException {
        User user = findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private User createUserFromData(String[] parts) {
        UUID id = UUID.fromString(parts[0]);
        String username = parts[1];
        String password = parts[2];
        String email = parts[3];
        String contact = parts[4];
        String userType = parts[5];
        return "ADMIN".equals(userType)
                ? new AdminUser(id, username, password, email, contact)
                : new TouristUser(id, username, password, email, contact);
    }
}