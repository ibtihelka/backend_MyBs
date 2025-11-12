package com.smldb2.demo.DTO;

public class LoginRequest {
    private String persoId;
    private String persoPassed;

    // Constructeurs
    public LoginRequest() {}

    public LoginRequest(String persoId, String persoPassed) {
        this.persoId = persoId;
        this.persoPassed = persoPassed;
    }

    // Getters et Setters
    public String getPersoId() {
        return persoId;
    }

    public void setPersoId(String persoId) {
        this.persoId = persoId;
    }

    public String getPersoPassed() {
        return persoPassed;
    }

    public void setPersoPassed(String persoPassed) {
        this.persoPassed = persoPassed;
    }
}