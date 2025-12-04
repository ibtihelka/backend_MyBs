package com.smldb2.demo.DTO;

import java.math.BigDecimal;

public class StatMedecinActeDTO {

    private String acte;
    private Long nombreActes;
    private String nomMedecin;
    private String villeMedecin;
    private BigDecimal totalDepense;
    private BigDecimal totalRembourse;

    // Constructeur pour la requête JPQL
    public StatMedecinActeDTO(String acte, Long nombreActes, String nomMedecin,
                              String villeMedecin, BigDecimal totalDepense,
                              BigDecimal totalRembourse) {
        this.acte = acte;
        this.nombreActes = nombreActes;
        this.nomMedecin = nomMedecin;
        this.villeMedecin = villeMedecin;
        this.totalDepense = totalDepense;
        this.totalRembourse = totalRembourse;
    }

    // Getters et Setters
    public String getActe() { return acte; }
    public void setActe(String acte) { this.acte = acte; }

    public Long getNombreActes() { return nombreActes; }
    public void setNombreActes(Long nombreActes) { this.nombreActes = nombreActes; }

    public String getNomMedecin() { return nomMedecin; }
    public void setNomMedecin(String nomMedecin) { this.nomMedecin = nomMedecin; }

    public String getVilleMedecin() { return villeMedecin; }
    public void setVilleMedecin(String villeMedecin) { this.villeMedecin = villeMedecin; }

    public BigDecimal getTotalDepense() { return totalDepense; }
    public void setTotalDepense(BigDecimal totalDepense) { this.totalDepense = totalDepense; }

    public BigDecimal getTotalRembourse() { return totalRembourse; }
    public void setTotalRembourse(BigDecimal totalRembourse) { this.totalRembourse = totalRembourse; }
}