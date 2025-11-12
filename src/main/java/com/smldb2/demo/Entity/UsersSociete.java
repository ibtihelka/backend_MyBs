package com.smldb2.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "userssociete")
public class UsersSociete {

    @Id
    @Column(name = "PERSO_ID")
    private String persoId;

    @Column(name = "PERSO_NAME")
    private String persoName;

    @Column(name = "PERSO_PASSED")
    private String persoPassed;

    @Column(name = "Type")
    private String type;

    // Constructeurs
    public UsersSociete() {}

    public UsersSociete(String persoId, String persoName, String persoPassed, String type) {
        this.persoId = persoId;
        this.persoName = persoName;
        this.persoPassed = persoPassed;
        this.type = type;
    }

    // Getters et Setters
    public String getPersoId() { return persoId; }
    public void setPersoId(String persoId) { this.persoId = persoId; }

    public String getPersoName() { return persoName; }
    public void setPersoName(String persoName) { this.persoName = persoName; }

    public String getPersoPassed() { return persoPassed; }
    public void setPersoPassed(String persoPassed) { this.persoPassed = persoPassed; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
