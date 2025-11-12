package com.smldb2.demo.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class BordereauSummaryDTO {
    private String refBordereau;
    private Date dateBordereau;
    private int nombreRemboursements;
    private BigDecimal montantDepense;
    private BigDecimal montantRembourse;
    private double tauxRemboursement;

    public BordereauSummaryDTO() {
    }

    public BordereauSummaryDTO(String refBordereau, Date dateBordereau, int nombreRemboursements,
                               BigDecimal montantDepense, BigDecimal montantRembourse, double tauxRemboursement) {
        this.refBordereau = refBordereau;
        this.dateBordereau = dateBordereau;
        this.nombreRemboursements = nombreRemboursements;
        this.montantDepense = montantDepense;
        this.montantRembourse = montantRembourse;
        this.tauxRemboursement = tauxRemboursement;
    }

    public String getRefBordereau() {
        return refBordereau;
    }

    public void setRefBordereau(String refBordereau) {
        this.refBordereau = refBordereau;
    }

    public Date getDateBordereau() {
        return dateBordereau;
    }

    public void setDateBordereau(Date dateBordereau) {
        this.dateBordereau = dateBordereau;
    }

    public int getNombreRemboursements() {
        return nombreRemboursements;
    }

    public void setNombreRemboursements(int nombreRemboursements) {
        this.nombreRemboursements = nombreRemboursements;
    }

    public BigDecimal getMontantDepense() {
        return montantDepense;
    }

    public void setMontantDepense(BigDecimal montantDepense) {
        this.montantDepense = montantDepense;
    }

    public BigDecimal getMontantRembourse() {
        return montantRembourse;
    }

    public void setMontantRembourse(BigDecimal montantRembourse) {
        this.montantRembourse = montantRembourse;
    }

    public double getTauxRemboursement() {
        return tauxRemboursement;
    }

    public void setTauxRemboursement(double tauxRemboursement) {
        this.tauxRemboursement = tauxRemboursement;
    }
}
