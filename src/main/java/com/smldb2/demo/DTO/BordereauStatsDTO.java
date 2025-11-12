package com.smldb2.demo.DTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BordereauStatsDTO {
    // Informations de base
    private String refBordereau;
    private Date dateBordereau;
    private String site;
    private String codeEntreprise;

    // Statistiques générales
    private int totalRemboursements;
    private BigDecimal montantTotalDepense;
    private BigDecimal montantTotalRembourse;
    private double tauxRemboursementMoyen;

    // Répartition des bénéficiaires
    private int nombreAdherents;
    private int nombreConjoints;
    private int nombreEnfants;
    private int nombreParents;

    // Analyse financière
    private BigDecimal montantMoyenRemboursement;
    private BigDecimal montantMaxRemboursement;
    private BigDecimal montantMinRemboursement;

    // Répartitions
    private Map<String, BigDecimal> repartitionMontantsParType;
    private Map<String, Integer> repartitionParStatut;
    private List<TopAdherentDTO> topAdherents;

    // Statistiques démographiques
    private Map<String, Integer> repartitionParSexe;
    private int moyenneAge;
    private Map<String, Integer> repartitionParTrancheAge;

    // Constructeurs
    public BordereauStatsDTO() {
    }

    // Getters et Setters
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCodeEntreprise() {
        return codeEntreprise;
    }

    public void setCodeEntreprise(String codeEntreprise) {
        this.codeEntreprise = codeEntreprise;
    }

    public int getTotalRemboursements() {
        return totalRemboursements;
    }

    public void setTotalRemboursements(int totalRemboursements) {
        this.totalRemboursements = totalRemboursements;
    }

    public BigDecimal getMontantTotalDepense() {
        return montantTotalDepense;
    }

    public void setMontantTotalDepense(BigDecimal montantTotalDepense) {
        this.montantTotalDepense = montantTotalDepense;
    }

    public BigDecimal getMontantTotalRembourse() {
        return montantTotalRembourse;
    }

    public void setMontantTotalRembourse(BigDecimal montantTotalRembourse) {
        this.montantTotalRembourse = montantTotalRembourse;
    }

    public double getTauxRemboursementMoyen() {
        return tauxRemboursementMoyen;
    }

    public void setTauxRemboursementMoyen(double tauxRemboursementMoyen) {
        this.tauxRemboursementMoyen = tauxRemboursementMoyen;
    }

    public int getNombreAdherents() {
        return nombreAdherents;
    }

    public void setNombreAdherents(int nombreAdherents) {
        this.nombreAdherents = nombreAdherents;
    }

    public int getNombreConjoints() {
        return nombreConjoints;
    }

    public void setNombreConjoints(int nombreConjoints) {
        this.nombreConjoints = nombreConjoints;
    }

    public int getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(int nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }

    public int getNombreParents() {
        return nombreParents;
    }

    public void setNombreParents(int nombreParents) {
        this.nombreParents = nombreParents;
    }

    public BigDecimal getMontantMoyenRemboursement() {
        return montantMoyenRemboursement;
    }

    public void setMontantMoyenRemboursement(BigDecimal montantMoyenRemboursement) {
        this.montantMoyenRemboursement = montantMoyenRemboursement;
    }

    public BigDecimal getMontantMaxRemboursement() {
        return montantMaxRemboursement;
    }

    public void setMontantMaxRemboursement(BigDecimal montantMaxRemboursement) {
        this.montantMaxRemboursement = montantMaxRemboursement;
    }

    public BigDecimal getMontantMinRemboursement() {
        return montantMinRemboursement;
    }

    public void setMontantMinRemboursement(BigDecimal montantMinRemboursement) {
        this.montantMinRemboursement = montantMinRemboursement;
    }

    public Map<String, BigDecimal> getRepartitionMontantsParType() {
        return repartitionMontantsParType;
    }

    public void setRepartitionMontantsParType(Map<String, BigDecimal> repartitionMontantsParType) {
        this.repartitionMontantsParType = repartitionMontantsParType;
    }

    public Map<String, Integer> getRepartitionParStatut() {
        return repartitionParStatut;
    }

    public void setRepartitionParStatut(Map<String, Integer> repartitionParStatut) {
        this.repartitionParStatut = repartitionParStatut;
    }

    public List<TopAdherentDTO> getTopAdherents() {
        return topAdherents;
    }

    public void setTopAdherents(List<TopAdherentDTO> topAdherents) {
        this.topAdherents = topAdherents;
    }

    public Map<String, Integer> getRepartitionParSexe() {
        return repartitionParSexe;
    }

    public void setRepartitionParSexe(Map<String, Integer> repartitionParSexe) {
        this.repartitionParSexe = repartitionParSexe;
    }

    public int getMoyenneAge() {
        return moyenneAge;
    }

    public void setMoyenneAge(int moyenneAge) {
        this.moyenneAge = moyenneAge;
    }

    public Map<String, Integer> getRepartitionParTrancheAge() {
        return repartitionParTrancheAge;
    }

    public void setRepartitionParTrancheAge(Map<String, Integer> repartitionParTrancheAge) {
        this.repartitionParTrancheAge = repartitionParTrancheAge;
    }
}
