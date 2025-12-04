package com.smldb2.demo.DTO;

import java.math.BigDecimal;

public class StatRangDTO {
    private String rang;
    private String libelleRang;
    private Long nombreRemboursements;
    private BigDecimal totalDepense;
    private BigDecimal totalRembourse;
    private BigDecimal difference;
    private Double pourcentageRemboursement;
    private Long nombreActes;

    // Constructeur complet
    public StatRangDTO(String rang, String libelleRang, Long nombreRemboursements,
                       BigDecimal totalDepense, BigDecimal totalRembourse,
                       BigDecimal difference, Double pourcentageRemboursement,
                       Long nombreActes) {
        this.rang = rang;
        this.libelleRang = libelleRang;
        this.nombreRemboursements = nombreRemboursements;
        this.totalDepense = totalDepense;
        this.totalRembourse = totalRembourse;
        this.difference = difference;
        this.pourcentageRemboursement = pourcentageRemboursement;
        this.nombreActes = nombreActes;
    }

    // Getters et Setters
    public String getRang() { return rang; }
    public void setRang(String rang) { this.rang = rang; }

    public String getLibelleRang() { return libelleRang; }
    public void setLibelleRang(String libelleRang) { this.libelleRang = libelleRang; }

    public Long getNombreRemboursements() { return nombreRemboursements; }
    public void setNombreRemboursements(Long nombreRemboursements) {
        this.nombreRemboursements = nombreRemboursements;
    }

    public BigDecimal getTotalDepense() { return totalDepense; }
    public void setTotalDepense(BigDecimal totalDepense) {
        this.totalDepense = totalDepense;
    }

    public BigDecimal getTotalRembourse() { return totalRembourse; }
    public void setTotalRembourse(BigDecimal totalRembourse) {
        this.totalRembourse = totalRembourse;
    }

    public BigDecimal getDifference() { return difference; }
    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    public Double getPourcentageRemboursement() { return pourcentageRemboursement; }
    public void setPourcentageRemboursement(Double pourcentageRemboursement) {
        this.pourcentageRemboursement = pourcentageRemboursement;
    }

    public Long getNombreActes() { return nombreActes; }
    public void setNombreActes(Long nombreActes) {
        this.nombreActes = nombreActes;
    }
}