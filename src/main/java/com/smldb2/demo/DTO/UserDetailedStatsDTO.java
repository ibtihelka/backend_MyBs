package com.smldb2.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * DTO pour les statistiques détaillées des adhérents
 */

public class UserDetailedStatsDTO {

    // Répartition par sexe : "M" -> nombre, "F" -> nombre
    private Map<String, Long> repartitionParSexe = new HashMap<>();

    // Répartition par situation familiale : "MARIE" -> nombre, "CELIBATAIRE" -> nombre, etc.
    private Map<String, Long> repartitionParSituationFamiliale = new HashMap<>();

    public Map<String, Long> getRepartitionParSexe() {
        return repartitionParSexe;
    }

    public void setRepartitionParSexe(Map<String, Long> repartitionParSexe) {
        this.repartitionParSexe = repartitionParSexe;
    }

    public Map<String, Long> getRepartitionParSituationFamiliale() {
        return repartitionParSituationFamiliale;
    }

    public void setRepartitionParSituationFamiliale(Map<String, Long> repartitionParSituationFamiliale) {
        this.repartitionParSituationFamiliale = repartitionParSituationFamiliale;
    }


    public UserDetailedStatsDTO(Map<String, Long> repartitionParSexe, Map<String, Long> repartitionParSituationFamiliale) {

        this.repartitionParSexe = repartitionParSexe;
        this.repartitionParSituationFamiliale = repartitionParSituationFamiliale;
    }

    public UserDetailedStatsDTO() {
    }
}