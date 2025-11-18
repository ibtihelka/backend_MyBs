package com.smldb2.demo.DTO;

import java.math.BigDecimal;

public class StatistiqueAdherentDTO {
    private String matricule;
    private String nomPrenom;
    private Long nombreBs;
    private BigDecimal totalDepense;
    private BigDecimal totalRembourse;
    private Double pourcentageRemboursement;
    private Integer nombreFamille;
    private BigDecimal plafondGlobal;

    // Constructeur par défaut
    public StatistiqueAdherentDTO() {
    }

    // Constructeur avec tous les paramètres
    public StatistiqueAdherentDTO(String matricule, String nomPrenom, Long nombreBs,
                                  BigDecimal totalDepense, BigDecimal totalRembourse,
                                  Double pourcentageRemboursement, Integer nombreFamille,
                                  BigDecimal plafondGlobal) {
        this.matricule = matricule;
        this.nomPrenom = nomPrenom;
        this.nombreBs = nombreBs;
        this.totalDepense = totalDepense;
        this.totalRembourse = totalRembourse;
        this.pourcentageRemboursement = pourcentageRemboursement;
        this.nombreFamille = nombreFamille;
        this.plafondGlobal = plafondGlobal;
    }

    // Getters et Setters
    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public Long getNombreBs() {
        return nombreBs;
    }

    public void setNombreBs(Long nombreBs) {
        this.nombreBs = nombreBs;
    }

    public BigDecimal getTotalDepense() {
        return totalDepense;
    }

    public void setTotalDepense(BigDecimal totalDepense) {
        this.totalDepense = totalDepense;
    }

    public BigDecimal getTotalRembourse() {
        return totalRembourse;
    }

    public void setTotalRembourse(BigDecimal totalRembourse) {
        this.totalRembourse = totalRembourse;
    }

    public Double getPourcentageRemboursement() {
        return pourcentageRemboursement;
    }

    public void setPourcentageRemboursement(Double pourcentageRemboursement) {
        this.pourcentageRemboursement = pourcentageRemboursement;
    }

    public Integer getNombreFamille() {
        return nombreFamille;
    }

    public void setNombreFamille(Integer nombreFamille) {
        this.nombreFamille = nombreFamille;
    }

    public BigDecimal getPlafondGlobal() {
        return plafondGlobal;
    }

    public void setPlafondGlobal(BigDecimal plafondGlobal) {
        this.plafondGlobal = plafondGlobal;
    }

    // toString pour le débogage
    @Override
    public String toString() {
        return "StatistiqueAdherentDTO{" +
                "matricule='" + matricule + '\'' +
                ", nomPrenom='" + nomPrenom + '\'' +
                ", nombreBs=" + nombreBs +
                ", totalDepense=" + totalDepense +
                ", totalRembourse=" + totalRembourse +
                ", pourcentageRemboursement=" + pourcentageRemboursement +
                ", nombreFamille=" + nombreFamille +
                ", plafondGlobal=" + plafondGlobal +
                '}';
    }

    // equals et hashCode (optionnel mais recommandé)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatistiqueAdherentDTO that = (StatistiqueAdherentDTO) o;

        return matricule != null ? matricule.equals(that.matricule) : that.matricule == null;
    }

    @Override
    public int hashCode() {
        return matricule != null ? matricule.hashCode() : 0;
    }
}