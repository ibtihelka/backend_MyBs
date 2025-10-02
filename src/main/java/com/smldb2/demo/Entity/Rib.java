package com.smldb2.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
@Entity
@Table(name = "rib")
public class Rib {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NumRIB")
    private Integer numRib;

    @Column(name = "AncienRIB")
    private String ancienRib;

    @Column(name = "NouveauRIB")
    private String nouveauRib;

    @Column(name = "PERSO_ID")
    private String persoId;

    @Column(name = "EXPORTED")
    private String exported;

    @Column(name = "DateCration")
    private Date dateCreation;

    // Constructeurs
    public Rib() {}

    // Getters et Setters
    public Integer getNumRib() { return numRib; }
    public void setNumRib(Integer numRib) { this.numRib = numRib; }

    public String getAncienRib() { return ancienRib; }
    public void setAncienRib(String ancienRib) { this.ancienRib = ancienRib; }

    public String getNouveauRib() { return nouveauRib; }
    public void setNouveauRib(String nouveauRib) { this.nouveauRib = nouveauRib; }

    public String getPersoId() { return persoId; }
    public void setPersoId(String persoId) { this.persoId = persoId; }

    public String getExported() { return exported; }
    public void setExported(String exported) { this.exported = exported; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
}