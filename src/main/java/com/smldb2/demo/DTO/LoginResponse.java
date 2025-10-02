package com.smldb2.demo.DTO;

import com.smldb2.demo.Entity.User;

public class LoginResponse {

    private String message;
    private boolean success;
    private User user;

    public LoginResponse(String message, boolean success, User user) {
        this.message = message;
        this.success = success;
        this.user = user;
    }


    public LoginResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
