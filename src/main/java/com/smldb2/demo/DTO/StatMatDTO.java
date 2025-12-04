package com.smldb2.demo.DTO;

import java.math.BigDecimal;

public class StatMatDTO {
    private String mat;
    private Long nombreRemboursements;
    private BigDecimal totalDepense;
    private BigDecimal totalRembourse;
    private BigDecimal difference;
    private Double pourcentageRemboursement;

    public StatMatDTO() {}

    public StatMatDTO(String mat, Long nombreRemboursements,
                      BigDecimal totalDepense, BigDecimal totalRembourse,
                      BigDecimal difference, Double pourcentageRemboursement) {
        this.mat = mat;
        this.nombreRemboursements = nombreRemboursements;
        this.totalDepense = totalDepense;
        this.totalRembourse = totalRembourse;
        this.difference = difference;
        this.pourcentageRemboursement = pourcentageRemboursement;
    }

    // Getters et Setters
    public String getMat() { return mat; }
    public void setMat(String mat) { this.mat = mat; }

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
}