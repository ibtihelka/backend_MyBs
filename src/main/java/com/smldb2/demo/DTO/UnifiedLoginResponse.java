package com.smldb2.demo.DTO;

import com.smldb2.demo.Entity.User;
import com.smldb2.demo.Entity.UsersAdmin;

public class UnifiedLoginResponse {
    private String message;
    private boolean success;
    private String userType; // "USER" ou "ADMIN"
    private User user;
    private UsersAdmin admin;

    public UnifiedLoginResponse() {}

    public UnifiedLoginResponse(String message, boolean success, String userType, User user, UsersAdmin admin) {
        this.message = message;
        this.success = success;
        this.userType = userType;
        this.user = user;
        this.admin = admin;
    }

    // Getters et Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public UsersAdmin getAdmin() { return admin; }
    public void setAdmin(UsersAdmin admin) { this.admin = admin; }
}