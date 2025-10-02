package com.smldb2.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "usersadmin")
public class UsersAdmin {
    @Id
    @Column(name = "PERSO_ID")
    private String persoId;

    @Column(name = "PERSO_NAME")
    private String persoName;

    @Column(name = "PERSO_PASSED")
    private String persoPassed;

    @Column(name = "Site")
    private String site;

    @Column(name = "Type")
    private String type;

    @Column(name = "CodeEntre")
    private String codeEntite;

    // Constructeurs
    public UsersAdmin() {}

    // Getters et Setters
    public String getPersoId() { return persoId; }
    public void setPersoId(String persoId) { this.persoId = persoId; }

    public String getPersoName() { return persoName; }
    public void setPersoName(String persoName) { this.persoName = persoName; }

    public String getPersoPassed() { return persoPassed; }
    public void setPersoPassed(String persoPassed) { this.persoPassed = persoPassed; }

    public String getSite() { return site; }
    public void setSite(String site) { this.site = site; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCodeEntite() { return codeEntite; }
    public void setCodeEntite(String codeEntite) { this.codeEntite = codeEntite; }
}