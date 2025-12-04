package com.smldb2.demo.DTO;

import java.util.Map;

public class StatistiquesAdherantDTO {
    // Année sélectionnée
    private Integer annee;

    // === STATISTIQUES GLOBALES (toutes années) ===
    private Long totalRemboursements;
    private Long totalReclamations;
    private Long totalContreVisites;

    // === STATISTIQUES PAR ANNÉE ===
    private Long remboursementsAnnee;
    private Long reclamationsAnnee;
    private Long contreVisitesAnnee;

    // === RÉCLAMATIONS DÉTAILLÉES (année sélectionnée) ===
    private Long reclamationsAvecReponse;
    private Long reclamationsSansReponse;

    // === RÉPARTITION PAR TYPE DE PRESTATAIRE (année sélectionnée) ===
    private Map<String, Long> repartitionParType; // adherant, conjoint, enfant

    // === ÉVOLUTION MENSUELLE (année sélectionnée) ===
    private Map<String, Long> remboursementsParMois;
    private Map<String, Long> reclamationsParMois;
    private Map<String, Long> contreVisitesParMois;

    // === ÉVOLUTION ANNUELLE (toutes années) ===
    private Map<Integer, Long> evolutionAnnuelleRemboursements;
    private Map<Integer, Long> evolutionAnnuelleReclamations;
    private Map<Integer, Long> evolutionAnnuelleContreVisites;

    // === STATISTIQUES SUPPLÉMENTAIRES ===
    private String moisPlusActif;
    private Double tauxContreVisite; // Pourcentage de contre-visites

    // Constructeurs
    public StatistiquesAdherantDTO() {}

    // Getters et Setters
    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Long getTotalRemboursements() {
        return totalRemboursements;
    }

    public void setTotalRemboursements(Long totalRemboursements) {
        this.totalRemboursements = totalRemboursements;
    }

    public Long getTotalReclamations() {
        return totalReclamations;
    }

    public void setTotalReclamations(Long totalReclamations) {
        this.totalReclamations = totalReclamations;
    }

    public Long getTotalContreVisites() {
        return totalContreVisites;
    }

    public void setTotalContreVisites(Long totalContreVisites) {
        this.totalContreVisites = totalContreVisites;
    }

    public Long getRemboursementsAnnee() {
        return remboursementsAnnee;
    }

    public void setRemboursementsAnnee(Long remboursementsAnnee) {
        this.remboursementsAnnee = remboursementsAnnee;
    }

    public Long getReclamationsAnnee() {
        return reclamationsAnnee;
    }

    public void setReclamationsAnnee(Long reclamationsAnnee) {
        this.reclamationsAnnee = reclamationsAnnee;
    }

    public Long getContreVisitesAnnee() {
        return contreVisitesAnnee;
    }

    public void setContreVisitesAnnee(Long contreVisitesAnnee) {
        this.contreVisitesAnnee = contreVisitesAnnee;
    }

    public Long getReclamationsAvecReponse() {
        return reclamationsAvecReponse;
    }

    public void setReclamationsAvecReponse(Long reclamationsAvecReponse) {
        this.reclamationsAvecReponse = reclamationsAvecReponse;
    }

    public Long getReclamationsSansReponse() {
        return reclamationsSansReponse;
    }

    public void setReclamationsSansReponse(Long reclamationsSansReponse) {
        this.reclamationsSansReponse = reclamationsSansReponse;
    }

    public Map<String, Long> getRepartitionParType() {
        return repartitionParType;
    }

    public void setRepartitionParType(Map<String, Long> repartitionParType) {
        this.repartitionParType = repartitionParType;
    }

    public Map<String, Long> getRemboursementsParMois() {
        return remboursementsParMois;
    }

    public void setRemboursementsParMois(Map<String, Long> remboursementsParMois) {
        this.remboursementsParMois = remboursementsParMois;
    }

    public Map<String, Long> getReclamationsParMois() {
        return reclamationsParMois;
    }

    public void setReclamationsParMois(Map<String, Long> reclamationsParMois) {
        this.reclamationsParMois = reclamationsParMois;
    }

    public Map<String, Long> getContreVisitesParMois() {
        return contreVisitesParMois;
    }

    public void setContreVisitesParMois(Map<String, Long> contreVisitesParMois) {
        this.contreVisitesParMois = contreVisitesParMois;
    }

    public Map<Integer, Long> getEvolutionAnnuelleRemboursements() {
        return evolutionAnnuelleRemboursements;
    }

    public void setEvolutionAnnuelleRemboursements(Map<Integer, Long> evolutionAnnuelleRemboursements) {
        this.evolutionAnnuelleRemboursements = evolutionAnnuelleRemboursements;
    }

    public Map<Integer, Long> getEvolutionAnnuelleReclamations() {
        return evolutionAnnuelleReclamations;
    }

    public void setEvolutionAnnuelleReclamations(Map<Integer, Long> evolutionAnnuelleReclamations) {
        this.evolutionAnnuelleReclamations = evolutionAnnuelleReclamations;
    }

    public Map<Integer, Long> getEvolutionAnnuelleContreVisites() {
        return evolutionAnnuelleContreVisites;
    }

    public void setEvolutionAnnuelleContreVisites(Map<Integer, Long> evolutionAnnuelleContreVisites) {
        this.evolutionAnnuelleContreVisites = evolutionAnnuelleContreVisites;
    }

    public String getMoisPlusActif() {
        return moisPlusActif;
    }

    public void setMoisPlusActif(String moisPlusActif) {
        this.moisPlusActif = moisPlusActif;
    }

    public Double getTauxContreVisite() {
        return tauxContreVisite;
    }

    public void setTauxContreVisite(Double tauxContreVisite) {
        this.tauxContreVisite = tauxContreVisite;
    }
}