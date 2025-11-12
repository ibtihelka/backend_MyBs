package com.smldb2.demo.DTO;

import com.smldb2.demo.Entity.User;
import com.smldb2.demo.Entity.UsersAdmin;
import com.smldb2.demo.Entity.Prestataire;
import com.smldb2.demo.Entity.UsersSociete;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnifiedLoginResponse {
    private String message;
    private boolean success;

    @JsonProperty("userType")
    private String userType; // Pour compatibilité

    @JsonProperty("role")
    private String role; // Nouveau champ pour Angular

    private User user;
    private UsersAdmin admin;
    private Prestataire prestataire;
    private UsersSociete usersSociete;

    public UnifiedLoginResponse() {}

    public UnifiedLoginResponse(String message, boolean success, String userType,
                                User user, UsersAdmin admin, Prestataire prestataire,
                                UsersSociete usersSociete) {
        this.message = message;
        this.success = success;
        this.userType = userType;
        this.role = userType; // ✅ Synchroniser role avec userType
        this.user = user;
        this.admin = admin;
        this.prestataire = prestataire;
        this.usersSociete = usersSociete;
    }

    // Getters et Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) {
        this.userType = userType;
        this.role = userType; // ✅ Synchroniser automatiquement
    }

    public String getRole() { return role; }
    public void setRole(String role) {
        this.role = role;
        this.userType = role; // ✅ Synchroniser automatiquement
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public UsersAdmin getAdmin() { return admin; }
    public void setAdmin(UsersAdmin admin) { this.admin = admin; }

    public Prestataire getPrestataire() { return prestataire; }
    public void setPrestataire(Prestataire prestataire) { this.prestataire = prestataire; }

    public UsersSociete getUsersSociete() { return usersSociete; }
    public void setUsersSociete(UsersSociete usersSociete) { this.usersSociete = usersSociete; }
}