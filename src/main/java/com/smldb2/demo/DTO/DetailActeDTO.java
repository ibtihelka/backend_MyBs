package com.smldb2.demo.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class DetailActeDTO {
    private Date dateBs;
    private String acte;
    private BigDecimal depense;
    private BigDecimal rembourse;
    private Double pourcentageRemboursement;

    // Constructeur par défaut
    public DetailActeDTO() {
    }

    // Constructeur avec tous les paramètres
    public DetailActeDTO(Date dateBs, String acte, BigDecimal depense,
                         BigDecimal rembourse, Double pourcentageRemboursement) {
        this.dateBs = dateBs;
        this.acte = acte;
        this.depense = depense;
        this.rembourse = rembourse;
        this.pourcentageRemboursement = pourcentageRemboursement;
    }

    // Getters et Setters
    public Date getDateBs() {
        return dateBs;
    }

    public void setDateBs(Date dateBs) {
        this.dateBs = dateBs;
    }

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

    public Double getPourcentageRemboursement() {
        return pourcentageRemboursement;
    }

    public void setPourcentageRemboursement(Double pourcentageRemboursement) {
        this.pourcentageRemboursement = pourcentageRemboursement;
    }

    // toString pour le débogage
    @Override
    public String toString() {
        return "DetailActeDTO{" +
                "dateBs=" + dateBs +
                ", acte='" + acte + '\'' +
                ", depense=" + depense +
                ", rembourse=" + rembourse +
                ", pourcentageRemboursement=" + pourcentageRemboursement +
                '}';
    }

    // equals et hashCode (optionnel mais recommandé)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetailActeDTO that = (DetailActeDTO) o;

        if (dateBs != null ? !dateBs.equals(that.dateBs) : that.dateBs != null) return false;
        return acte != null ? acte.equals(that.acte) : that.acte == null;
    }

    @Override
    public int hashCode() {
        int result = dateBs != null ? dateBs.hashCode() : 0;
        result = 31 * result + (acte != null ? acte.hashCode() : 0);
        return result;
    }
}