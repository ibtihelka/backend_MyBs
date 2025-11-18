package com.smldb2.demo.DTO;


import java.math.BigDecimal;
import java.util.List;

public class ReportingAnnuelDTO {
    private Integer annee;
    private String codeSociete;
    private BigDecimal totalDepense;
    private BigDecimal totalRembourse;
    private BigDecimal difference;
    private Double pourcentageRemboursement;
    private Integer nombreActes;
    private List<ActeAnnuelDTO> actes;
    private StatistiquesPrestataireDTO statistiquesPrestataire;

    public ReportingAnnuelDTO() {}

    public ReportingAnnuelDTO(Integer annee, String codeSociete, BigDecimal totalDepense,
                              BigDecimal totalRembourse, BigDecimal difference,
                              Double pourcentageRemboursement, Integer nombreActes,
                              List<ActeAnnuelDTO> actes, StatistiquesPrestataireDTO statistiquesPrestataire) {
        this.annee = annee;
        this.codeSociete = codeSociete;
        this.totalDepense = totalDepense;
        this.totalRembourse = totalRembourse;
        this.difference = difference;
        this.pourcentageRemboursement = pourcentageRemboursement;
        this.nombreActes = nombreActes;
        this.actes = actes;
        this.statistiquesPrestataire = statistiquesPrestataire;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public String getCodeSociete() {
        return codeSociete;
    }

    public void setCodeSociete(String codeSociete) {
        this.codeSociete = codeSociete;
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

    public Integer getNombreActes() {
        return nombreActes;
    }

    public void setNombreActes(Integer nombreActes) {
        this.nombreActes = nombreActes;
    }

    public List<ActeAnnuelDTO> getActes() {
        return actes;
    }

    public void setActes(List<ActeAnnuelDTO> actes) {
        this.actes = actes;
    }

    public StatistiquesPrestataireDTO getStatistiquesPrestataire() {
        return statistiquesPrestataire;
    }

    public void setStatistiquesPrestataire(StatistiquesPrestataireDTO statistiquesPrestataire) {
        this.statistiquesPrestataire = statistiquesPrestataire;
    }
}
