package com.smldb2.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "famille")
@IdClass(Famille.FamilleId.class)
public class Famille {

    @Id
    @Column(name = "PRENOM_PRESTATAIRE")
    private String prenomPrestataire;

    @Id
    @Column(name = "PERSO_ID")
    private String persoId;

    @Column(name = "TYP_PRESTATIRE")
    @Convert(converter = TypePrestataireConverter.class)
    private TypePrestataire typPrestataire;

    @Column(name = "DAT_NAIS")
    private Date datNais;

    @Column(name = "SEXE")
    private String sexe;

    // Relation avec User
    @ManyToOne
    @JoinColumn(name = "PERSO_ID", insertable = false, updatable = false)
    @JsonBackReference("user-familles")
    private User user;

    // Constructeurs
    public Famille() {}

    // Getters et Setters
    public String getPrenomPrestataire() { return prenomPrestataire; }
    public void setPrenomPrestataire(String prenomPrestataire) { this.prenomPrestataire = prenomPrestataire; }

    public TypePrestataire getTypPrestataire() { return typPrestataire; }
    public void setTypPrestataire(TypePrestataire typPrestataire) { this.typPrestataire = typPrestataire; }

    public String getPersoId() { return persoId; }
    public void setPersoId(String persoId) { this.persoId = persoId; }

    public Date getDatNais() { return datNais; }
    public void setDatNais(Date datNais) { this.datNais = datNais; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // ⭐ Classe interne pour la clé composite
    public static class FamilleId implements Serializable {
        private String prenomPrestataire;
        private String persoId;

        public FamilleId() {}

        public FamilleId(String prenomPrestataire, String persoId) {
            this.prenomPrestataire = prenomPrestataire;
            this.persoId = persoId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FamilleId that = (FamilleId) o;
            return Objects.equals(prenomPrestataire, that.prenomPrestataire) &&
                    Objects.equals(persoId, that.persoId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prenomPrestataire, persoId);
        }

        // Getters et Setters
        public String getPrenomPrestataire() { return prenomPrestataire; }
        public void setPrenomPrestataire(String prenomPrestataire) { this.prenomPrestataire = prenomPrestataire; }

        public String getPersoId() { return persoId; }
        public void setPersoId(String persoId) { this.persoId = persoId; }
    }
}