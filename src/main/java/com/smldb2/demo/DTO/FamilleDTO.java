package com.smldb2.demo.DTO;

import com.smldb2.demo.Entity.TypePrestataire;

import java.util.Date;

public class FamilleDTO {

    private String prenomPrestataire;
    private TypePrestataire typPrestataire;
    private String persoId;
    private Date datNais;
    private String sexe;





    public FamilleDTO(Date datNais, String persoId, String prenomPrestataire, String sexe, TypePrestataire typPrestataire) {
        this.datNais = datNais;
        this.persoId = persoId;
        this.prenomPrestataire = prenomPrestataire;
        this.sexe = sexe;
        this.typPrestataire = typPrestataire;
    }

    public FamilleDTO() {
    }

    public TypePrestataire getTypPrestataire() {
        return typPrestataire;
    }

    public void setTypPrestataire(TypePrestataire typPrestataire) {
        this.typPrestataire = typPrestataire;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getPrenomPrestataire() {
        return prenomPrestataire;
    }

    public void setPrenomPrestataire(String prenomPrestataire) {
        this.prenomPrestataire = prenomPrestataire;
    }

    public String getPersoId() {
        return persoId;
    }

    public void setPersoId(String persoId) {
        this.persoId = persoId;
    }

    public Date getDatNais() {
        return datNais;
    }

    public void setDatNais(Date datNais) {
        this.datNais = datNais;
    }
}
