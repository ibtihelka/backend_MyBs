package com.smldb2.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "stat_act_ann")
public class StatActAnn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "codesociete")
    private String codeSociete;

    @Column(name = "acte")
    private String acte;

    @Column(name = "depense")
    private BigDecimal depense;

    @Column(name = "rembourser")
    private BigDecimal rembourser;

    // Constructeurs
    public StatActAnn() {}

    public StatActAnn(Long id, Integer annee, String codeSociete, String acte,
                      BigDecimal depense, BigDecimal rembourser) {
        this.id = id;
        this.annee = annee;
        this.codeSociete = codeSociete;
        this.acte = acte;
        this.depense = depense;
        this.rembourser = rembourser;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
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

    public BigDecimal getRembourser() {
        return rembourser;
    }

    public void setRembourser(BigDecimal rembourser) {
        this.rembourser = rembourser;
    }
}