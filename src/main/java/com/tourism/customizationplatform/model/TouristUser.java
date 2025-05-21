package com.tourism.customizationplatform.model;

import java.util.UUID;

public class TouristUser extends User {
    public TouristUser(UUID id, String username, String password, String email, String contact) {
        super(id, username, password, email, contact);
    }

    @Override
    public String getUserType() {
        return "TOURIST";
    }
}