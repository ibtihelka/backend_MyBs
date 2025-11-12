package com.smldb2.demo.DTO;

import java.util.Map;

public class FamilleStatsDTO {
    private int totalFamilles;
    private double moyenneAge;
    private Map<String, Integer> repartitionParType;
    private Map<String, Integer> repartitionParSexe;
    private Map<String, Integer> repartitionParTrancheAge;

    // Getters et setters
    public int getTotalFamilles() { return totalFamilles; }
    public void setTotalFamilles(int totalFamilles) { this.totalFamilles = totalFamilles; }

    public double getMoyenneAge() { return moyenneAge; }
    public void setMoyenneAge(double moyenneAge) { this.moyenneAge = moyenneAge; }

    public Map<String, Integer> getRepartitionParType() { return repartitionParType; }
    public void setRepartitionParType(Map<String, Integer> repartitionParType) { this.repartitionParType = repartitionParType; }

    public Map<String, Integer> getRepartitionParSexe() { return repartitionParSexe; }
    public void setRepartitionParSexe(Map<String, Integer> repartitionParSexe) { this.repartitionParSexe = repartitionParSexe; }

    public Map<String, Integer> getRepartitionParTrancheAge() { return repartitionParTrancheAge; }
    public void setRepartitionParTrancheAge(Map<String, Integer> repartitionParTrancheAge) { this.repartitionParTrancheAge = repartitionParTrancheAge; }
}
