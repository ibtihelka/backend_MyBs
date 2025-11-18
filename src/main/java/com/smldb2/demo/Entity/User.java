package com.smldb2.demo.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "PERSO_ID")
    private String persoId;

    @Column(name = "PERSO_NAME")
    private String persoName;

    @Column(name = "PERSO_PASSED")
    private String persoPassed;

    @Column(name = "EMail")
    private String email;

    @Column(name = "SEXE")
    private String sexe;

    @Column(name = "DAT_NAIS")
    private Date datNais;

    @Column(name = "PAYS_NAIS")
    private String paysNais;

    @Column(name = "GOUV_NAIS")
    private String gouvNais;

    @Column(name = "CIN")
    private String cin;

    @Column(name = "DATE_PIECE_IDENETITE")
    private Date datePieceIdentite;

    @Column(name = "SITUATION_FAMILIALE")
    private String situationFamiliale;

    @Column(name = "ADRESS")
    private String adresse;

    @Column(name = "CONTACT")
    private String contact;

    @Column(name = "RIB")
    private String rib;

    @Column(name = "SITUATION_ADHESION")
    private String situationAdhesion;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "Token")
    private String token;



    @Column(name = "date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    // Getter et Setter
    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    // Optionnel : Auto-set date de création
    @PrePersist
    protected void onCreate() {
        dateCreation = new Date();
    }

    // Relations
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("user-familles")
    private List<Famille> familles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("user-remboursements")
    private List<Remboursement> remboursements = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("user-suggestions")
    private List<Suggestion> suggestions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("user-reclamations")
    private List<Reclamation> reclamations = new ArrayList<>();




    // Constructeurs
    public User() {}

    // Getters et Setters (tous les anciens + le nouveau)
    public String getPersoId() { return persoId; }
    public void setPersoId(String persoId) { this.persoId = persoId; }

    public String getPersoName() { return persoName; }
    public void setPersoName(String persoName) { this.persoName = persoName; }

    public String getPersoPassed() { return persoPassed; }
    public void setPersoPassed(String persoPassed) { this.persoPassed = persoPassed; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public Date getDatNais() { return datNais; }
    public void setDatNais(Date datNais) { this.datNais = datNais; }

    public String getPaysNais() { return paysNais; }
    public void setPaysNais(String paysNais) { this.paysNais = paysNais; }

    public String getGouvNais() { return gouvNais; }
    public void setGouvNais(String gouvNais) { this.gouvNais = gouvNais; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public Date getDatePieceIdentite() { return datePieceIdentite; }
    public void setDatePieceIdentite(Date datePieceIdentite) { this.datePieceIdentite = datePieceIdentite; }

    public String getSituationFamiliale() { return situationFamiliale; }
    public void setSituationFamiliale(String situationFamiliale) { this.situationFamiliale = situationFamiliale; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getRib() { return rib; }
    public void setRib(String rib) { this.rib = rib; }

    public String getSituationAdhesion() { return situationAdhesion; }
    public void setSituationAdhesion(String situationAdhesion) { this.situationAdhesion = situationAdhesion; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }


    public List<Remboursement> getRemboursements() { return remboursements; }
    public void setRemboursements(List<Remboursement> remboursements) { this.remboursements = remboursements; }

    public List<Suggestion> getSuggestions() { return suggestions; }
    public void setSuggestions(List<Suggestion> suggestions) { this.suggestions = suggestions; }

    public List<Reclamation> getReclamations() { return reclamations; }
    public void setReclamations(List<Reclamation> reclamations) { this.reclamations = reclamations; }

    public List<Famille> getFamilles() { return familles; }
    public void setFamilles(List<Famille> familles) { this.familles = familles; }
}