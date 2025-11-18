package com.smldb2.demo.DTO;

import java.math.BigDecimal;
import java.util.List;

public class ReportingResponseDTO {
    private BigDecimal totalDepense;
    private BigDecimal totalRembourse;
    private BigDecimal difference;
    private Double pourcentageRemboursement;
    private Integer nombreAdherents;
    private Integer nombreActesTotal;
    private List<StatAdherentDTO> adherents;
    private List<StatActeDTO> actes;

    // Constructeur vide
    public ReportingResponseDTO() {}

    // Constructeur avec tous les paramètres
    public ReportingResponseDTO(BigDecimal totalDepense, BigDecimal totalRembourse,
                                BigDecimal difference, Double pourcentageRemboursement,
                                Integer nombreAdherents, Integer nombreActesTotal,
                                List<StatAdherentDTO> adherents, List<StatActeDTO> actes) {
        this.totalDepense = totalDepense;
        this.totalRembourse = totalRembourse;
        this.difference = difference;
        this.pourcentageRemboursement = pourcentageRemboursement;
        this.nombreAdherents = nombreAdherents;
        this.nombreActesTotal = nombreActesTotal;
        this.adherents = adherents;
        this.actes = actes;
    }

    // Getters et Setters
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

    public Integer getNombreAdherents() {
        return nombreAdherents;
    }

    public void setNombreAdherents(Integer nombreAdherents) {
        this.nombreAdherents = nombreAdherents;
    }

    public Integer getNombreActesTotal() {
        return nombreActesTotal;
    }

    public void setNombreActesTotal(Integer nombreActesTotal) {
        this.nombreActesTotal = nombreActesTotal;
    }

    public List<StatAdherentDTO> getAdherents() {
        return adherents;
    }

    public void setAdherents(List<StatAdherentDTO> adherents) {
        this.adherents = adherents;
    }

    public List<StatActeDTO> getActes() {
        return actes;
    }

    public void setActes(List<StatActeDTO> actes) {
        this.actes = actes;
    }
}