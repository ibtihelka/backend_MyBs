package com.smldb2.demo.DTO;

import java.util.Date;

public class ReclamationDTO {
    private Long numReclamation;
    private String refBsPhys;
    private String persoId;
    private String titreReclamation;
    private String texteReclamation;
    private String responseRec;
    private String exported;
    private Date dateCreation;

    // Informations du remboursement associé (optionnel)
    private String nomPrenPrest;
    private Date datBs;

    // Constructeurs
    public ReclamationDTO() {}

    public ReclamationDTO(String refBsPhys, String persoId, String titreReclamation, String texteReclamation) {
        this.refBsPhys = refBsPhys;
        this.persoId = persoId;
        this.titreReclamation = titreReclamation;
        this.texteReclamation = texteReclamation;
    }

    // Getters et Setters
    public Long getNumReclamation() {
        return numReclamation;
    }

    public void setNumReclamation(Long numReclamation) {
        this.numReclamation = numReclamation;
    }

    public String getRefBsPhys() {
        return refBsPhys;
    }

    public void setRefBsPhys(String refBsPhys) {
        this.refBsPhys = refBsPhys;
    }

    public String getPersoId() {
        return persoId;
    }

    public void setPersoId(String persoId) {
        this.persoId = persoId;
    }

    public String getTitreReclamation() {
        return titreReclamation;
    }

    public void setTitreReclamation(String titreReclamation) {
        this.titreReclamation = titreReclamation;
    }

    public String getTexteReclamation() {
        return texteReclamation;
    }

    public void setTexteReclamation(String texteReclamation) {
        this.texteReclamation = texteReclamation;
    }

    public String getResponseRec() {
        return responseRec;
    }

    public void setResponseRec(String responseRec) {
        this.responseRec = responseRec;
    }

    public String getExported() {
        return exported;
    }

    public void setExported(String exported) {
        this.exported = exported;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getNomPrenPrest() {
        return nomPrenPrest;
    }

    public void setNomPrenPrest(String nomPrenPrest) {
        this.nomPrenPrest = nomPrenPrest;
    }

    public Date getDatBs() {
        return datBs;
    }

    public void setDatBs(Date datBs) {
        this.datBs = datBs;
    }
}