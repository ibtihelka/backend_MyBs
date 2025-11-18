package com.smldb2.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "acte")

public class Acte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ACTE")
    private Long idActe;

    @Column(name = "REF_BS_PHYS", insertable = false, updatable = false)
    private String refBsPhys;

    @Column(name = "CODE_ACTE")
    private String codeActe;  // Ex: AM, AMM, AMO, AN, ANE, CL, CR, C1, C2, C3

    @Column(name = "DEPENSE")
    private BigDecimal depense;  // Montant de la dépense

    @Column(name = "REMBOURSEMENT")
    private BigDecimal rembourser;  // Montant remboursé

    // Relation Many-to-One avec Remboursement
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REF_BS_PHYS", referencedColumnName = "REF_BS_PHYS")
    @JsonBackReference("remboursement-actes")
    private Remboursement remboursement;

    public Acte(String codeActe, BigDecimal depense, Long idActe, String refBsPhys, Remboursement remboursement, BigDecimal rembourser) {
        this.codeActe = codeActe;
        this.depense = depense;
        this.idActe = idActe;
        this.refBsPhys = refBsPhys;
        this.remboursement = remboursement;
        this.rembourser = rembourser;
    }


    public Acte() {
    }

    public String getCodeActe() {
        return codeActe;
    }

    public void setCodeActe(String codeActe) {
        this.codeActe = codeActe;
    }

    public BigDecimal getDepense() {
        return depense;
    }

    public void setDepense(BigDecimal depense) {
        this.depense = depense;
    }

    public Long getIdActe() {
        return idActe;
    }

    public void setIdActe(Long idActe) {
        this.idActe = idActe;
    }

    public String getRefBsPhys() {
        return refBsPhys;
    }

    public void setRefBsPhys(String refBsPhys) {
        this.refBsPhys = refBsPhys;
    }

    public Remboursement getRemboursement() {
        return remboursement;
    }

    public void setRemboursement(Remboursement remboursement) {
        this.remboursement = remboursement;
    }

    public BigDecimal getRembourser() {
        return rembourser;
    }

    public void setRembourser(BigDecimal rembourser) {
        this.rembourser = rembourser;
    }
}