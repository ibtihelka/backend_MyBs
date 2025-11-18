package com.smldb2.demo.DTO;


import java.math.BigDecimal;

public class ActeDetailDTO {
    private String acte;
    private String dateBs;  // ATTENTION: C'est un String, pas un Date !
    private BigDecimal depense;
    private BigDecimal rembourse;
    private Double pourcentageRemboursement;

    // Constructeur vide
    public ActeDetailDTO() {}

    // Constructeur avec tous les paramètres
    public ActeDetailDTO(String acte, String dateBs, BigDecimal depense,
                         BigDecimal rembourse, Double pourcentageRemboursement) {
        this.acte = acte;
        this.dateBs = dateBs;
        this.depense = depense;
        this.rembourse = rembourse;
        this.pourcentageRemboursement = pourcentageRemboursement;
    }

    // Getters et Setters
    public String getActe() {
        return acte;
    }

    public void setActe(String acte) {
        this.acte = acte;
    }

    public String getDateBs() {
        return dateBs;
    }

    public void setDateBs(String dateBs) {
        this.dateBs = dateBs;
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

    public Double getPourcentageRemboursement() {
        return pourcentageRemboursement;
    }

    public void setPourcentageRemboursement(Double pourcentageRemboursement) {
        this.pourcentageRemboursement = pourcentageRemboursement;
    }
}