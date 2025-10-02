package com.smldb2.demo.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "tel")
public class Tel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NumTel")
    private Integer numTel;

    @Column(name = "AncienTel")
    private String ancienTel;

    @Column(name = "NouveauTel")
    private String nouveauTel;

    @Column(name = "PERSO_ID")
    private String persoId;

    @Column(name = "EXPORTED")
    private String exported;

    @Column(name = "DateCration")
    private Date dateCreation;

    // Constructeurs
    public Tel() {}

    // Getters et Setters
    public Integer getNumTel() { return numTel; }
    public void setNumTel(Integer numTel) { this.numTel = numTel; }

    public String getAncienTel() { return ancienTel; }
    public void setAncienTel(String ancienTel) { this.ancienTel = ancienTel; }

    public String getNouveauTel() { return nouveauTel; }
    public void setNouveauTel(String nouveauTel) { this.nouveauTel = nouveauTel; }

    public String getPersoId() { return persoId; }
    public void setPersoId(String persoId) { this.persoId = persoId; }

    public String getExported() { return exported; }
    public void setExported(String exported) { this.exported = exported; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }
}