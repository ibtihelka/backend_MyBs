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

    // NOUVELLES COLONNES
    @Column(name = "MF")
    private String mf;

    @Column(name = "COD_ACT")
    private String codAct;

    @Column(name = "COD_DOCT_ACT")
    private String codDoctAct;

    // NOUVELLE COLONNE NUM_BS
    @Column(name = "NUM_BS")
    private String numBs;

    // Constructeurs
    public StatActDate() {}

    public StatActDate(Long id, LocalDate dateBs, String codeSociete, String acte,
                       BigDecimal depense, BigDecimal rembourse, String matricule,
                       String mf, String codAct, String codDoctAct, String numBs) {
        this.id = id;
        this.dateBs = dateBs;
        this.codeSociete = codeSociete;
        this.acte = acte;
        this.depense = depense;
        this.rembourse = rembourse;
        this.matricule = matricule;
        this.mf = mf;
        this.codAct = codAct;
        this.codDoctAct = codDoctAct;
        this.numBs = numBs;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDateBs() { return dateBs; }
    public void setDateBs(LocalDate dateBs) { this.dateBs = dateBs; }

    public String getCodeSociete() { return codeSociete; }
    public void setCodeSociete(String codeSociete) { this.codeSociete = codeSociete; }

    public String getActe() { return acte; }
    public void setActe(String acte) { this.acte = acte; }

    public BigDecimal getDepense() { return depense; }
    public void setDepense(BigDecimal depense) { this.depense = depense; }

    public BigDecimal getRembourse() { return rembourse; }
    public void setRembourse(BigDecimal rembourse) { this.rembourse = rembourse; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getMf() { return mf; }
    public void setMf(String mf) { this.mf = mf; }

    public String getCodAct() { return codAct; }
    public void setCodAct(String codAct) { this.codAct = codAct; }

    public String getCodDoctAct() { return codDoctAct; }
    public void setCodDoctAct(String codDoctAct) { this.codDoctAct = codDoctAct; }

    public String getNumBs() { return numBs; }
    public void setNumBs(String numBs) { this.numBs = numBs; }
}
