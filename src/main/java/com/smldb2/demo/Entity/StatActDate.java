package com.smldb2.demo.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "stat_act_date")
public class StatActDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_bs")
    private LocalDate dateBs;

    @Column(name = "code_societe")
    private String codeSociete;

    @Column(name = "acte")
    private String acte;

    @Column(name = "depense")
    private BigDecimal depense;

    @Column(name = "rembourse")
    private BigDecimal rembourse;

    @Column(name = "matricule")
    private String matricule;

    // Constructeurs
    public StatActDate() {}

    public StatActDate(Long id, LocalDate dateBs, String codeSociete, String acte,
                       BigDecimal depense, BigDecimal rembourse, String matricule) {
        this.id = id;
        this.dateBs = dateBs;
        this.codeSociete = codeSociete;
        this.acte = acte;
        this.depense = depense;
        this.rembourse = rembourse;
        this.matricule = matricule;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateBs() {
        return dateBs;
    }

    public void setDateBs(LocalDate dateBs) {
        this.dateBs = dateBs;
    }

    public String getCodeSociete() {
        return codeSociete;
    }

    public void setCodeSociete(String codeSociete) {
        this.codeSociete = codeSociete;
    }

    public String getActe() {
        return acte;
    }

    public void setActe(String acte) {
        this.acte = acte;
    }

    public BigDecimal getDepense() {
        return depense;
    }

    public void setDepense(BigDecimal depense) {
        this.depense = depense;
    }

    public BigDecimal getRembourse() {
        return rembourse;
    }

    public void setRembourse(BigDecimal rembourse) {
        this.rembourse = rembourse;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}