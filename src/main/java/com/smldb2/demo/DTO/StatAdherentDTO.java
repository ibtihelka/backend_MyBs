package com.smldb2.demo.DTO;


import java.math.BigDecimal;

public class StatAdherentDTO {
    private String matricule;
    private String nomAdherent;
    private Integer nombreFamille;
    private BigDecimal depense;
    private BigDecimal rembourse;
    private BigDecimal difference;
    private Double pourcentageRemboursement;
    private Long nombreActes;
    private BigDecimal plafondGlobal;

    public StatAdherentDTO() {}

    public StatAdherentDTO(String matricule, String nomAdherent, Integer nombreFamille,
                           BigDecimal depense, BigDecimal rembourse,
                           BigDecimal difference, Double pourcentageRemboursement,
                           Long nombreActes, BigDecimal plafondGlobal) {
        this.matricule = matricule;
        this.nomAdherent = nomAdherent;
        this.nombreFamille = nombreFamille;
        this.depense = depense;
        this.rembourse = rembourse;
        this.difference = difference;
        this.pourcentageRemboursement = pourcentageRemboursement;
        this.nombreActes = nombreActes;
        this.plafondGlobal = plafondGlobal;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomAdherent() {
        return nomAdherent;
    }

    public void setNomAdherent(String nomAdherent) {
        this.nomAdherent = nomAdherent;
    }

    public Integer getNombreFamille() {
        return nombreFamille;
    }

    public void setNombreFamille(Integer nombreFamille) {
        this.nombreFamille = nombreFamille;
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

    public Long getNombreActes() {
        return nombreActes;
    }

    public void setNombreActes(Long nombreActes) {
        this.nombreActes = nombreActes;
    }

    public BigDecimal getPlafondGlobal() {
        return plafondGlobal;
    }

    public void setPlafondGlobal(BigDecimal plafondGlobal) {
        this.plafondGlobal = plafondGlobal;
    }
}