
package com.smldb2.demo.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "famille")
public class Famille {
    @Id
    @Column(name = "PRENOM_PRESTATAIRE")
    private String prenomPrestataire;

    @Column(name = "TYP_PRESTATIRE")
    @Convert(converter = TypePrestataireConverter.class)
    private TypePrestataire typPrestataire;

    @Column(name = "PERSO_ID")
    private String persoId;

    @Column(name = "DAT_NAIS")
    private Date datNais;

    @Column(name = "SEXE")
    private String sexe;

    // Relation avec User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSO_ID", insertable = false, updatable = false)
    @JsonBackReference
    private User user;




    // Constructeurs
    public Famille() {}

    // Getters et Setters
    public String getPrenomPrestataire() { return prenomPrestataire; }
    public void setPrenomPrestataire(String prenomPrestataire) { this.prenomPrestataire = prenomPrestataire; }

    public TypePrestataire getTypPrestataire() {
        return typPrestataire;
    }

    public void setTypPrestataire(TypePrestataire typPrestataire) {
        this.typPrestataire = typPrestataire;
    }

    public String getPersoId() { return persoId; }
    public void setPersoId(String persoId) { this.persoId = persoId; }

    public Date getDatNais() { return datNais; }
    public void setDatNais(Date datNais) { this.datNais = datNais; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}