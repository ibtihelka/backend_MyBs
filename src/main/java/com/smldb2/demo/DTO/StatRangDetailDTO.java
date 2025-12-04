package com.smldb2.demo.DTO;

import java.math.BigDecimal;

/**
 * DTO pour les statistiques détaillées par RANG
 * Contient des informations plus complètes sur chaque type de bénéficiaire
 */
public class StatRangDetailDTO {
    private String rang;
    private String typeLabel;  // "Adhérent", "Enfant 1", "Conjoint", etc.
    private Long nombreRemboursements;
    private Long nombreActes;
    private BigDecimal totalDepense;
    private BigDecimal totalRembourse;
    private BigDecimal difference;
    private Double pourcentageRemboursement;
    private BigDecimal depenseMoyenne;
    private BigDecimal rembourseMoyen;

    public StatRangDetailDTO() {}

    public StatRangDetailDTO(String rang, String typeLabel, Long nombreRemboursements,
                             Long nombreActes, BigDecimal totalDepense,
                             BigDecimal totalRembourse, BigDecimal difference,
                             Double pourcentageRemboursement,
                             BigDecimal depenseMoyenne, BigDecimal rembourseMoyen) {
        this.rang = rang;
        this.typeLabel = typeLabel;
        this.nombreRemboursements = nombreRemboursements;
        this.nombreActes = nombreActes;
        this.totalDepense = totalDepense;
        this.totalRembourse = totalRembourse;
        this.difference = difference;
        this.pourcentageRemboursement = pourcentageRemboursement;
        this.depenseMoyenne = depenseMoyenne;
        this.rembourseMoyen = rembourseMoyen;
    }

    // Getters et Setters
    public String getRang() { return rang; }
    public void setRang(String rang) { this.rang = rang; }

    public String getTypeLabel() { return typeLabel; }
    public void setTypeLabel(String typeLabel) { this.typeLabel = typeLabel; }

    public Long getNombreRemboursements() { return nombreRemboursements; }
    public void setNombreRemboursements(Long nombreRemboursements) {
        this.nombreRemboursements = nombreRemboursements;
    }

    public Long getNombreActes() { return nombreActes; }
    public void setNombreActes(Long nombreActes) { this.nombreActes = nombreActes; }

    public BigDecimal getTotalDepense() { return totalDepense; }
    public void setTotalDepense(BigDecimal totalDepense) { this.totalDepense = totalDepense; }

    public BigDecimal getTotalRembourse() { return totalRembourse; }
    public void setTotalRembourse(BigDecimal totalRembourse) {
        this.totalRembourse = totalRembourse;
    }

    public BigDecimal getDifference() { return difference; }
    public void setDifference(BigDecimal difference) { this.difference = difference; }

    public Double getPourcentageRemboursement() { return pourcentageRemboursement; }
    public void setPourcentageRemboursement(Double pourcentageRemboursement) {
        this.pourcentageRemboursement = pourcentageRemboursement;
    }

    public BigDecimal getDepenseMoyenne() { return depenseMoyenne; }
    public void setDepenseMoyenne(BigDecimal depenseMoyenne) {
        this.depenseMoyenne = depenseMoyenne;
    }

    public BigDecimal getRembourseMoyen() { return rembourseMoyen; }
    public void setRembourseMoyen(BigDecimal rembourseMoyen) {
        this.rembourseMoyen = rembourseMoyen;
    }
}