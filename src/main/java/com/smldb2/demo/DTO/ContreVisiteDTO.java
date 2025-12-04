package com.smldb2.demo.DTO;

import java.util.Date;

public class ContreVisiteDTO {
    private String refBsPhys;
    private String codDoctCv;
    private Date datBs;
    private String statut; // "Oui" ou "Non"
    private Date prev; // Date de prévision de remboursement
    private Date dateBordereau;

    public ContreVisiteDTO() {}

    public ContreVisiteDTO(String refBsPhys, String codDoctCv, Date datBs, String statut, Date prev, Date dateBordereau) {
        this.refBsPhys = refBsPhys;
        this.codDoctCv = codDoctCv;
        this.datBs = datBs;
        this.statut = statut;
        this.prev = prev;
        this.dateBordereau = dateBordereau;
    }

    // Getters et Setters
    public String getRefBsPhys() { return refBsPhys; }
    public void setRefBsPhys(String refBsPhys) { this.refBsPhys = refBsPhys; }

    public String getCodDoctCv() { return codDoctCv; }
    public void setCodDoctCv(String codDoctCv) { this.codDoctCv = codDoctCv; }

    public Date getDatBs() { return datBs; }
    public void setDatBs(Date datBs) { this.datBs = datBs; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public Date getPrev() { return prev; }
    public void setPrev(Date prev) { this.prev = prev; }

    public Date getDateBordereau() { return dateBordereau; }
    public void setDateBordereau(Date dateBordereau) { this.dateBordereau = dateBordereau; }
}