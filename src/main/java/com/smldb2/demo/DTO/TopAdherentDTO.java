package com.smldb2.demo.DTO;

import java.math.BigDecimal;

public class TopAdherentDTO {
    private String nomPrenPrest;
    private int nombreRemboursements;
    private BigDecimal montantTotal;

    // Constructeur par défaut
    public TopAdherentDTO() {
    }

    // Constructeur avec tous les paramètres (ORDRE CORRECT)
    public TopAdherentDTO(String nomPrenPrest, int nombreRemboursements, BigDecimal montantTotal) {
        this.nomPrenPrest = nomPrenPrest;
        this.nombreRemboursements = nombreRemboursements;
        this.montantTotal = montantTotal;
    }

    // Getters et Setters
    public String getNomPrenPrest() {
        return nomPrenPrest;
    }

    public void setNomPrenPrest(String nomPrenPrest) {
        this.nomPrenPrest = nomPrenPrest;
    }

    public int getNombreRemboursements() {
        return nombreRemboursements;
    }

    public void setNombreRemboursements(int nombreRemboursements) {
        this.nombreRemboursements = nombreRemboursements;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }
}