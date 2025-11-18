package com.smldb2.demo.DTO;

import java.math.BigDecimal;

public class StatistiqueActeDTO {
    private String acte;
    private BigDecimal depense;
    private BigDecimal rembourse;
    private Long nombre;
    private Double pourcentageRemboursement;

    // Constructeur par défaut
    public StatistiqueActeDTO() {
    }

    // Constructeur avec tous les paramètres
    public StatistiqueActeDTO(String acte, BigDecimal depense, BigDecimal rembourse,
                              Long nombre, Double pourcentageRemboursement) {
        this.acte = acte;
        this.depense = depense;
        this.rembourse = rembourse;
        this.nombre = nombre;
        this.pourcentageRemboursement = pourcentageRemboursement;
    }

    // Getters et Setters
    public String getActe() {
        return acte;
    }

    public void setActe(String acte) {
        this.acte = acte;
    }

    public BigDecimal getDepense() {
        return depense;
    }

    public void setDepense(BigDecimal depense) {
        this.depense = depense;
    }

    public BigDecimal getRembourse() {
        return rembourse;
    }

    public void setRembourse(BigDecimal rembourse) {
        this.rembourse = rembourse;
    }

    public Long getNombre() {
        return nombre;
    }

    public void setNombre(Long nombre) {
        this.nombre = nombre;
    }

    public Double getPourcentageRemboursement() {
        return pourcentageRemboursement;
    }

    public void setPourcentageRemboursement(Double pourcentageRemboursement) {
        this.pourcentageRemboursement = pourcentageRemboursement;
    }

    // toString pour le débogage
    @Override
    public String toString() {
        return "StatistiqueActeDTO{" +
                "acte='" + acte + '\'' +
                ", depense=" + depense +
                ", rembourse=" + rembourse +
                ", nombre=" + nombre +
                ", pourcentageRemboursement=" + pourcentageRemboursement +
                '}';
    }

    // equals et hashCode (optionnel mais recommandé)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatistiqueActeDTO that = (StatistiqueActeDTO) o;

        return acte != null ? acte.equals(that.acte) : that.acte == null;
    }

    @Override
    public int hashCode() {
        return acte != null ? acte.hashCode() : 0;
    }
}