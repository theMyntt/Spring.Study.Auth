package com.example.demo.enums;

public enum UserRoles {
    ADMIN("admin"),
    USER("user");

    private final String role;

    public String getRole() {
        return this.role;
    }

    UserRoles(String role) {
        this.role = role;
    }
}
