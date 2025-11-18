package com.smldb2.demo.Entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "prestataire")
public class Prestataire {

    @Id
    @Column(name = "PERSO_ID")
    private String persoId;

    @Column(name = "PERSO_PASSED")
    private String persoPassed;

    @Column(name = "NOM")
    private String nom;

    @Column(name = "ROLE")
    private String role; // "DENTISTE" ou "OPTICIEN"

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CONTACT")
    private String contact;

    @Column(name = "ADRESSE")
    private String adresse;

    @Column(name = "SEXE")
    private String sexe;

    @Column(name = "MATRICULE_FISCALE")
    private String matriculeFiscale;

    @Column(name = "date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @PrePersist
    protected void onCreate() {
        dateCreation = new Date();
    }

    public Prestataire() {}

    // --- Getters et Setters ---
    public String getPersoId() { return persoId; }
    public void setPersoId(String persoId) { this.persoId = persoId; }

    public String getPersoPassed() { return persoPassed; }
    public void setPersoPassed(String persoPassed) { this.persoPassed = persoPassed; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getMatriculeFiscale() { return matriculeFiscale; }
    public void setMatriculeFiscale(String matriculeFiscale) { this.matriculeFiscale = matriculeFiscale; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
}
