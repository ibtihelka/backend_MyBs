package com.smldb2.demo.DTO;

import java.math.BigDecimal;
import java.util.Map;

public class RemboursementStatsDTO {
    private int totalRemboursements;
    private BigDecimal montantTotalRemb;
    private Map<String, Integer> repartitionParMois;
    private Map<String, Integer> repartitionParStatut;

    // Getters et setters
    public int getTotalRemboursements() { return totalRemboursements; }
    public void setTotalRemboursements(int totalRemboursements) { this.totalRemboursements = totalRemboursements; }

    public BigDecimal getMontantTotalRemb() { return montantTotalRemb; }
    public void setMontantTotalRemb(BigDecimal montantTotalRemb) { this.montantTotalRemb = montantTotalRemb; }

    public Map<String, Integer> getRepartitionParMois() { return repartitionParMois; }
    public void setRepartitionParMois(Map<String, Integer> repartitionParMois) { this.repartitionParMois = repartitionParMois; }

    public Map<String, Integer> getRepartitionParStatut() { return repartitionParStatut; }
    public void setRepartitionParStatut(Map<String, Integer> repartitionParStatut) { this.repartitionParStatut = repartitionParStatut; }
}
