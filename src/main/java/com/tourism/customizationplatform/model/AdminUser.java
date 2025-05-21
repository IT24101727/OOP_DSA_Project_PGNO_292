package com.tourism.customizationplatform.model;

import java.util.UUID;

public class AdminUser extends User {
    public AdminUser(UUID id, String username, String password, String email, String contact) {
        super(id, username, password, email, contact);
    }

    @Override
    public String getUserType() {
        return "ADMIN";
    }
}