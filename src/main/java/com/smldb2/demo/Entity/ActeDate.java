package com.smldb2.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "acte_date")

public class ActeDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE_BS")
    @Temporal(TemporalType.DATE)
    private Date dateBs;

    @Column(name = "CODE_SOCIETE")
    private String codeSociete;

    @Column(name = "ACTE")
    private String acte;

    @Column(name = "DEPENSE")
    private BigDecimal depense;

    @Column(name = "REMBOURSE")
    private BigDecimal rembourse;

    @Column(name = "MATRICULE")
    private String matricule; // CIN de l'adhérent


    public ActeDate() {

    }

    public ActeDate(String acte, String codeSociete, Date dateBs, BigDecimal depense, Long id, String matricule, BigDecimal rembourse) {
        this.acte = acte;
        this.codeSociete = codeSociete;
        this.dateBs = dateBs;
        this.depense = depense;
        this.id = id;
        this.matricule = matricule;
        this.rembourse = rembourse;
    }

    public String getActe() {
        return acte;
    }

    public void setActe(String acte) {
        this.acte = acte;
    }

    public String getCodeSociete() {
        return codeSociete;
    }

    public void setCodeSociete(String codeSociete) {
        this.codeSociete = codeSociete;
    }

    public Date getDateBs() {
        return dateBs;
    }

    public void setDateBs(Date dateBs) {
        this.dateBs = dateBs;
    }

    public BigDecimal getDepense() {
        return depense;
    }

    public void setDepense(BigDecimal depense) {
        this.depense = depense;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public BigDecimal getRembourse() {
        return rembourse;
    }

    public void setRembourse(BigDecimal rembourse) {
        this.rembourse = rembourse;
    }
}