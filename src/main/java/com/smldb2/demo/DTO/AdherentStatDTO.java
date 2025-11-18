package com.smldb2.demo.DTO;


import java.math.BigDecimal;

public class AdherentStatDTO {
    private String matricule;
    private String nomPrenom;
    private BigDecimal totalDepense;
    private BigDecimal totalRembourse;
    private Double pourcentageRemb;
    private Integer nombreActes;

    // Constructeurs
    public AdherentStatDTO() {}

    public AdherentStatDTO(String matricule, String nomPrenom, BigDecimal totalDepense,
                           BigDecimal totalRembourse, Double pourcentageRemb, Integer nombreActes) {
        this.matricule = matricule;
        this.nomPrenom = nomPrenom;
        this.totalDepense = totalDepense;
        this.totalRembourse = totalRembourse;
        this.pourcentageRemb = pourcentageRemb;
        this.nombreActes = nombreActes;
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

    public Double getPourcentageRemb() {
        return pourcentageRemb;
    }

    public void setPourcentageRemb(Double pourcentageRemb) {
        this.pourcentageRemb = pourcentageRemb;
    }

    public Integer getNombreActes() {
        return nombreActes;
    }

    public void setNombreActes(Integer nombreActes) {
        this.nombreActes = nombreActes;
    }
}