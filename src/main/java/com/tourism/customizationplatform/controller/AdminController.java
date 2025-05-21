package com.tourism.customizationplatform.controller;

import com.tourism.customizationplatform.model.*;
import com.tourism.customizationplatform.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<String> addAdmin(@RequestBody AdminUser admin, @RequestHeader("Admin-Id") String requestingAdminId) throws IOException {
        admin.setId(UUID.randomUUID());
        adminService.addAdmin(admin, requestingAdminId);
        return ResponseEntity.ok("Admin added successfully");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllAdmins() throws IOException {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdmin(@PathVariable String id, @RequestBody AdminUser admin, @RequestHeader("Admin-Id") String requestingAdminId) throws IOException {
        admin.setId(UUID.fromString(id));
        adminService.updateAdmin(admin, requestingAdminId);
        return ResponseEntity.ok("Admin updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable String id, @RequestHeader("Admin-Id") String requestingAdminId) throws IOException {
        adminService.deleteAdmin(id, requestingAdminId);
        return ResponseEntity.ok("Admin deleted successfully");
    }
}