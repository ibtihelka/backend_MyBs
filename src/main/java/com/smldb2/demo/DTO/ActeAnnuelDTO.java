package com.smldb2.demo.DTO;


import java.math.BigDecimal;

public class ActeAnnuelDTO {
    private String acte;
    private BigDecimal depense;
    private BigDecimal rembourse;
    private BigDecimal difference;
    private Double pourcentageRemboursement;

    public ActeAnnuelDTO() {}

    public ActeAnnuelDTO(String acte, BigDecimal depense, BigDecimal rembourse,
                         BigDecimal difference, Double pourcentageRemboursement) {
        this.acte = acte;
        this.depense = depense;
        this.rembourse = rembourse;
        this.difference = difference;
        this.pourcentageRemboursement = pourcentageRemboursement;
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

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    public Double getPourcentageRemboursement() {
        return pourcentageRemboursement;
    }

    public void setPourcentageRemboursement(Double pourcentageRemboursement) {
        this.pourcentageRemboursement = pourcentageRemboursement;
    }
}
