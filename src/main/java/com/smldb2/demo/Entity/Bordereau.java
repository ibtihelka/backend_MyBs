package com.smldb2.demo.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bordereau")
public class Bordereau {

    @Id
    @Column(name = "REF_BORDEREAU")
    private String refBordereau;  // NUM_BORD_SOC dans le CSV (clé primaire)

    @Column(name = "NUM_BORD")
    private String numBord;       // NUM_BORD dans le CSV

    @Column(name = "DATE_BORDEREAU")
    private Date dateBordereau;   // DAT_BORD dans le CSV

    @Column(name = "CODE_SOC")
    private String codeSoc;       // COD_SOC dans le CSV

    @Column(name = "CODE_SIT")
    private String codeSit;       // COD_SIT dans le CSV

    @OneToMany(mappedBy = "bordereau", cascade = CascadeType.ALL)
    @JsonManagedReference("bordereau-remboursements")
    private List<Remboursement> remboursements;

    public Bordereau() {}

    public Bordereau(String refBordereau, String numBord, Date dateBordereau, String codeSoc, String codeSit) {
        this.refBordereau = refBordereau;
        this.numBord = numBord;
        this.dateBordereau = dateBordereau;
        this.codeSoc = codeSoc;
        this.codeSit = codeSit;
    }

    public String getRefBordereau() {
        return refBordereau;
    }

    public void setRefBordereau(String refBordereau) {
        this.refBordereau = refBordereau;
    }

    public String getNumBord() {
        return numBord;
    }

    public void setNumBord(String numBord) {
        this.numBord = numBord;
    }

    public Date getDateBordereau() {
        return dateBordereau;
    }

    public void setDateBordereau(Date dateBordereau) {
        this.dateBordereau = dateBordereau;
    }

    public String getCodeSoc() {
        return codeSoc;
    }

    public void setCodeSoc(String codeSoc) {
        this.codeSoc = codeSoc;
    }

    public String getCodeSit() {
        return codeSit;
    }

    public void setCodeSit(String codeSit) {
        this.codeSit = codeSit;
    }

    public List<Remboursement> getRemboursements() {
        return remboursements;
    }

    public void setRemboursements(List<Remboursement> remboursements) {
        this.remboursements = remboursements;
    }
}
