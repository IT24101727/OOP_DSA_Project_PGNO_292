package com.tourism.customizationplatform.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "userType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TouristUser.class, name = "TOURIST"),
        @JsonSubTypes.Type(value = AdminUser.class, name = "ADMIN")
})

public abstract class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String contact;

    protected User(UUID id, String username, String password, String email, String contact) {
        this.id = id;
        this.username = username;
        this.password = password; // In production, hash passwords
        this.email = email;
        this.contact = contact;
    }

    // Getters and Setters (Encapsulation)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    // Polymorphic method
    public abstract String getUserType();

    public String toFileFormat() {
        return String.join(",", id.toString(), username, password, email, contact, getUserType());
    }
}