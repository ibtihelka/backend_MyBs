package com.smldb2.demo.DTO;



import com.smldb2.demo.Entity.RapportContreVisite;

public class RapportCreationRequest {
    private String matriculeAdherent;
    private String refBsPhys;
    private String prestataireId;
    private RapportContreVisite rapport;

    public String getMatriculeAdherent() {
        return matriculeAdherent;
    }

    public void setMatriculeAdherent(String matriculeAdherent) {
        this.matriculeAdherent = matriculeAdherent;
    }

    public String getRefBsPhys() {
        return refBsPhys;
    }

    public void setRefBsPhys(String refBsPhys) {
        this.refBsPhys = refBsPhys;
    }

    public String getPrestataireId() {
        return prestataireId;
    }

    public void setPrestataireId(String prestataireId) {
        this.prestataireId = prestataireId;
    }

    public RapportContreVisite getRapport() {
        return rapport;
    }

    public void setRapport(RapportContreVisite rapport) {
        this.rapport = rapport;
    }
}

