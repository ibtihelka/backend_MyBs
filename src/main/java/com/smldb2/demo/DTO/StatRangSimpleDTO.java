package com.smldb2.demo.DTO;

public class StatRangSimpleDTO {
    private String rang;
    private String libelleRang;
    private Long nombreRemboursements;
    private Double pourcentage; // Pourcentage par rapport au total

    public StatRangSimpleDTO() {}

    public StatRangSimpleDTO(String rang, String libelleRang, Long nombreRemboursements, Double pourcentage) {
        this.rang = rang;
        this.libelleRang = libelleRang;
        this.nombreRemboursements = nombreRemboursements;
        this.pourcentage = pourcentage;
    }

    // Getters et Setters
    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public String getLibelleRang() {
        return libelleRang;
    }

    public void setLibelleRang(String libelleRang) {
        this.libelleRang = libelleRang;
    }

    public Long getNombreRemboursements() {
        return nombreRemboursements;
    }

    public void setNombreRemboursements(Long nombreRemboursements) {
        this.nombreRemboursements = nombreRemboursements;
    }

    public Double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(Double pourcentage) {
        this.pourcentage = pourcentage;
    }
}