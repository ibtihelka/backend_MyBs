package com.smldb2.demo.DTO;

import java.math.BigDecimal;

public class StatistiqueGeneraleDTO {
    private Long nombreTotalRemboursements;
    private Long nombreTotalActes;
    private BigDecimal montantTotalDepense;
    private BigDecimal montantTotalRembourse;

    public StatistiqueGeneraleDTO() {}

    public StatistiqueGeneraleDTO(Long nombreTotalRemboursements, Long nombreTotalActes,
                                  BigDecimal montantTotalDepense, BigDecimal montantTotalRembourse) {
        this.nombreTotalRemboursements = nombreTotalRemboursements;
        this.nombreTotalActes = nombreTotalActes;
        this.montantTotalDepense = montantTotalDepense;
        this.montantTotalRembourse = montantTotalRembourse;
    }

    // Getters et Setters
    public Long getNombreTotalRemboursements() { return nombreTotalRemboursements; }
    public void setNombreTotalRemboursements(Long nombreTotalRemboursements) {
        this.nombreTotalRemboursements = nombreTotalRemboursements;
    }

    public Long getNombreTotalActes() { return nombreTotalActes; }
    public void setNombreTotalActes(Long nombreTotalActes) {
        this.nombreTotalActes = nombreTotalActes;
    }

    public BigDecimal getMontantTotalDepense() { return montantTotalDepense; }
    public void setMontantTotalDepense(BigDecimal montantTotalDepense) {
        this.montantTotalDepense = montantTotalDepense;
    }

    public BigDecimal getMontantTotalRembourse() { return montantTotalRembourse; }
    public void setMontantTotalRembourse(BigDecimal montantTotalRembourse) {
        this.montantTotalRembourse = montantTotalRembourse;
    }
}