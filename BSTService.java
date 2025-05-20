package com.tourism.customizationplatform.service;

import com.tourism.customizationplatform.model.*;
import com.tourism.customizationplatform.util.FileUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class BSTService {
    private Node root;
    private final String packagesFilePath;

    private static class Node {
        TravelPackage data;
        Node left, right;

        Node(TravelPackage data) {
            this.data = data;
            left = right = null;
        }
    }

    @Autowired
    public BSTService(@Value("${file.paths.packages}") String packagesFilePath) {
        this.packagesFilePath = packagesFilePath;
        loadPackagesFromFile();
    }

    private void loadPackagesFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(packagesFilePath));
            for (String line : lines) {
                System.out.println("Parsing package line: " + line);
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    TravelPackage pkg = parts[5].equals("DOMESTIC") ?
                            new DomesticPackage(UUID.fromString(parts[0]), parts[1], Double.parseDouble(parts[2]), parts[3], Integer.parseInt(parts[4])) :
                            new InternationalPackage(UUID.fromString(parts[0]), parts[1], Double.parseDouble(parts[2]), parts[3], Integer.parseInt(parts[4]));
                    root = insertRec(root, pkg);
                } else {
                    System.err.println("Invalid package line format: " + line);
                }
            }
            System.out.println("Loaded packages into BST");
        } catch (IOException e) {
            System.err.println("Error loading packages: " + e.getMessage());
        }
    }

    public void addPackage(TravelPackage pkg) throws IOException {
        try {
            root = insertRec(root, pkg);
            FileUtil.writeToFile(packagesFilePath, pkg.toFileFormat(), true);
            System.out.println("Added package to BST: " + pkg.getName());
        } catch (Exception e) {
            System.err.println("Error adding package: " + e.getMessage());
            throw new IOException("Failed to add package", e);
        }
    }

    public List<TravelPackage> getAllPackages() {
        List<TravelPackage> packages = new ArrayList<>();
        inorderTraversal(root, packages);
        System.out.println("Packages retrieved: " + packages);
        return packages;
    }

    private Node insertRec(Node root, TravelPackage pkg) {
        if (root == null) {
            return new Node(pkg);
        }
        if (pkg.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, pkg);
        } else if (pkg.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, pkg);
        }
        return root;
    }

    private void inorderTraversal(Node node, List<TravelPackage> packages) {
        if (node != null) {
            inorderTraversal(node.left, packages);
            packages.add(node.data);
            inorderTraversal(node.right, packages);
        }
    }

    // Update
    public void updatePackage(TravelPackage updatedPkg) throws IOException {
        root = deleteRec(root, updatedPkg.getId());
        root = insertRec(root, updatedPkg);
        saveToFile(); // Save once after update
    }

    // Delete
    public void deletePackage(String id) throws IOException {
        root = deleteRec(root, UUID.fromString(id));
        saveToFile();
    }

    private Node deleteRec(Node root, UUID id) {
        if (root == null) return null;

        if (id.compareTo(root.data.getId()) < 0) {
            root.left = deleteRec(root.left, id);
        } else if (id.compareTo(root.data.getId()) > 0) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            root.data = minValue(root.right);
            root.right = deleteRec(root.right, root.data.getId());
        }
        return root;
    }

    private TravelPackage minValue(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.data;
    }

    private void saveToFile() throws IOException {
        List<TravelPackage> packages = getAllPackages();
        List<String> lines = packages.stream()
                .map(TravelPackage::toFileFormat)
                .toList();
        FileUtil.overwriteFile(packagesFilePath, lines);
    }

    // -------------------- Package Creation --------------------

    private TravelPackage createPackageFromData(String[] parts) {
        UUID id = UUID.fromString(parts[0]);
        String name = parts[1];
        double price = Double.parseDouble(parts[2]);
        String destination = parts[3];
        int duration = Integer.parseInt(parts[4]);
        String type = parts[5].trim();

        return "DOMESTIC".equalsIgnoreCase(type)
                ? new DomesticPackage(id, name, price, destination, duration)
                : new InternationalPackage(id, name, price, destination, duration);
    }
}

