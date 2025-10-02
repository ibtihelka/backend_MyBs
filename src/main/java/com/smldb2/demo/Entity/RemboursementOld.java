package com.smldb2.demo.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "remboursement_old")
public class RemboursementOld {
    @Id
    @Column(name = "REF_BS_PHYS")
    private String refBsPhys;

    @Column(name = "PERSO_ID")
    private String persoId;

    @Column(name = "NOM_PREN_PREST")
    private String nomPrenPrest;

    @Column(name = "DAT_BS")
    private Date datBs;

    @Column(name = "MNT_BS")
    private BigDecimal mntBs;

    @Column(name = "MNT_BS_REMB")
    private BigDecimal mntBsRemb;

    @Column(name = "STAT_BS")
    private String statBs;

    @Column(name = "RefBorderau")
    private String refBorderau;

    @Column(name = "Site")
    private String site;

    @Column(name = "DES_SIT")
    private String desSit;

    @Column(name = "CodeEntreprise")
    private String codeEntreprise;

    @Column(name = "OBSERVATION")
    private String observation;

    @Column(name = "DateBordereau")
    private Date dateBordereau;

    // Constructeurs
    public RemboursementOld() {}

    public String getCodeEntreprise() {
        return codeEntreprise;
    }

    public void setCodeEntreprise(String codeEntreprise) {
        this.codeEntreprise = codeEntreprise;
    }

    public Date getDatBs() {
        return datBs;
    }

    public void setDatBs(Date datBs) {
        this.datBs = datBs;
    }

    public Date getDateBordereau() {
        return dateBordereau;
    }

    public void setDateBordereau(Date dateBordereau) {
        this.dateBordereau = dateBordereau;
    }

    public String getDesSit() {
        return desSit;
    }

    public void setDesSit(String desSit) {
        this.desSit = desSit;
    }

    public BigDecimal getMntBs() {
        return mntBs;
    }

    public void setMntBs(BigDecimal mntBs) {
        this.mntBs = mntBs;
    }

    public BigDecimal getMntBsRemb() {
        return mntBsRemb;
    }

    public void setMntBsRemb(BigDecimal mntBsRemb) {
        this.mntBsRemb = mntBsRemb;
    }

    public String getNomPrenPrest() {
        return nomPrenPrest;
    }

    public void setNomPrenPrest(String nomPrenPrest) {
        this.nomPrenPrest = nomPrenPrest;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getPersoId() {
        return persoId;
    }

    public void setPersoId(String persoId) {
        this.persoId = persoId;
    }

    public String getRefBorderau() {
        return refBorderau;
    }

    public void setRefBorderau(String refBorderau) {
        this.refBorderau = refBorderau;
    }

    public String getRefBsPhys() {
        return refBsPhys;
    }

    public void setRefBsPhys(String refBsPhys) {
        this.refBsPhys = refBsPhys;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getStatBs() {
        return statBs;
    }

    public void setStatBs(String statBs) {
        this.statBs = statBs;
    }



}

