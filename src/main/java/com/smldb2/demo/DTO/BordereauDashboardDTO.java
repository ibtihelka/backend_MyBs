package com.smldb2.demo.DTO;

import java.util.Date;

public class BordereauDashboardDTO {

    private String refBordereau;
    private Date dateBordereau;
    private Double montantDepense;
    private Double montantRembourse;
    private Integer totalRemboursements;
    private FamilleStats familleStats;

    // Constructeurs
    public BordereauDashboardDTO() {
        this.familleStats = new FamilleStats();
    }

    public BordereauDashboardDTO(String refBordereau, Date dateBordereau,
                                 Double montantDepense, Double montantRembourse,
                                 Integer totalRemboursements) {
        this.refBordereau = refBordereau;
        this.dateBordereau = dateBordereau;
        this.montantDepense = montantDepense;
        this.montantRembourse = montantRembourse;
        this.totalRemboursements = totalRemboursements;
        this.familleStats = new FamilleStats();
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

    public Double getMontantDepense() {
        return montantDepense;
    }

    public void setMontantDepense(Double montantDepense) {
        this.montantDepense = montantDepense;
    }

    public Double getMontantRembourse() {
        return montantRembourse;
    }

    public void setMontantRembourse(Double montantRembourse) {
        this.montantRembourse = montantRembourse;
    }

    public Integer getTotalRemboursements() {
        return totalRemboursements;
    }

    public void setTotalRemboursements(Integer totalRemboursements) {
        this.totalRemboursements = totalRemboursements;
    }

    public FamilleStats getFamilleStats() {
        return familleStats;
    }

    public void setFamilleStats(FamilleStats familleStats) {
        this.familleStats = familleStats;
    }

    // Méthode utilitaire
    public Double getTauxRemboursement() {
        if (montantDepense != null && montantDepense > 0 && montantRembourse != null) {
            return (montantRembourse / montantDepense) * 100;
        }
        return 0.0;
    }

    /**
     * Classe interne pour les statistiques de famille
     */
    public static class FamilleStats {
        private Integer totalFamilles;
        private Integer nombreConjoints;
        private Integer nombreEnfants;
        private Integer nombreAdherents;
        private Integer nombreParents;
        private Integer moyenneAge;

        // Constructeur
        public FamilleStats() {
            this.totalFamilles = 0;
            this.nombreConjoints = 0;
            this.nombreEnfants = 0;
            this.nombreAdherents = 0;
            this.nombreParents = 0;
            this.moyenneAge = 0;
        }

        // Getters et Setters
        public Integer getTotalFamilles() {
            return totalFamilles;
        }

        public void setTotalFamilles(Integer totalFamilles) {
            this.totalFamilles = totalFamilles;
        }

        public Integer getNombreConjoints() {
            return nombreConjoints;
        }

        public void setNombreConjoints(Integer nombreConjoints) {
            this.nombreConjoints = nombreConjoints;
        }

        public Integer getNombreEnfants() {
            return nombreEnfants;
        }

        public void setNombreEnfants(Integer nombreEnfants) {
            this.nombreEnfants = nombreEnfants;
        }

        public Integer getNombreAdherents() {
            return nombreAdherents;
        }

        public void setNombreAdherents(Integer nombreAdherents) {
            this.nombreAdherents = nombreAdherents;
        }

        public Integer getNombreParents() {
            return nombreParents;
        }

        public void setNombreParents(Integer nombreParents) {
            this.nombreParents = nombreParents;
        }

        public Integer getMoyenneAge() {
            return moyenneAge;
        }

        public void setMoyenneAge(Integer moyenneAge) {
            this.moyenneAge = moyenneAge;
        }

        @Override
        public String toString() {
            return "FamilleStats{" +
                    "totalFamilles=" + totalFamilles +
                    ", nombreConjoints=" + nombreConjoints +
                    ", nombreEnfants=" + nombreEnfants +
                    ", nombreAdherents=" + nombreAdherents +
                    ", nombreParents=" + nombreParents +
                    ", moyenneAge=" + moyenneAge +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BordereauDashboardDTO{" +
                "refBordereau='" + refBordereau + '\'' +
                ", dateBordereau=" + dateBordereau +
                ", montantDepense=" + montantDepense +
                ", montantRembourse=" + montantRembourse +
                ", totalRemboursements=" + totalRemboursements +
                ", tauxRemboursement=" + getTauxRemboursement() + "%" +
                ", familleStats=" + familleStats +
                '}';
    }
}