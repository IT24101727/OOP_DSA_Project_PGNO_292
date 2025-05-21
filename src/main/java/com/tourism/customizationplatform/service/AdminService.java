package com.tourism.customizationplatform.service;

import com.tourism.customizationplatform.model.*;
import com.tourism.customizationplatform.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Value("${file.paths.admins}")
    private String adminsFilePath;

    @Autowired
    private UserService userService;

    // Create
    public void addAdmin(AdminUser admin, String requestingAdminId) throws IOException {
        User requester = userService.findUserById(requestingAdminId);
        if (!(requester instanceof AdminUser)) {
            throw new SecurityException("Only admins can add admins");
        }
        userService.registerUser(admin);
        FileUtil.writeToFile(adminsFilePath, admin.getId().toString(), true);
    }

    // Read
    public List<User> getAllAdmins() throws IOException {
        List<String> adminIds = FileUtil.readFromFile(adminsFilePath);
        List<User> allUsers = userService.getAllUsers();
        List<User> admins = new ArrayList<>();
        for (User user : allUsers) {
            if (adminIds.contains(user.getId().toString())) {
                admins.add(user);
            }
        }
        return admins;
    }

    // Update
    public void updateAdmin(AdminUser updatedAdmin, String requestingAdminId) throws IOException {
        User requester = userService.findUserById(requestingAdminId);
        if (!(requester instanceof AdminUser)) {
            throw new SecurityException("Only admins can update admins");
        }
        userService.updateUser(updatedAdmin);
    }

    // Delete
    public void deleteAdmin(String id, String requestingAdminId) throws IOException {
        User requester = userService.findUserById(requestingAdminId);
        if (!(requester instanceof AdminUser)) {
            throw new SecurityException("Only admins can delete admins");
        }
        userService.deleteUser(id);
        List<String> adminIds = FileUtil.readFromFile(adminsFilePath);
        adminIds.remove(id);
        FileUtil.overwriteFile(adminsFilePath, adminIds);
    }
}