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
    private String refBordereau;  // ✅ Changé de refBorderau à refBordereau

    @Column(name = "DATE_BORDEREAU")
    private Date dateBordereau;

    // Relation : un bordereau contient plusieurs remboursements
    @OneToMany(mappedBy = "bordereau", cascade = CascadeType.ALL)
    @JsonManagedReference("bordereau-remboursements")
    private List<Remboursement> remboursements;

    // Constructeurs
    public Bordereau() {}

    public Bordereau(String refBordereau, Date dateBordereau) {

        this.refBordereau = refBordereau;
        this.dateBordereau = dateBordereau;
    }

    // Getters et setters

    public String getRefBordereau() {
        return refBordereau;
    }

    public void setRefBordereau(String refBordereau) {
        this.refBordereau = refBordereau;
    }

    public Date getDateBordereau() { return dateBordereau; }
    public void setDateBordereau(Date dateBordereau) { this.dateBordereau = dateBordereau; }

    public List<Remboursement> getRemboursements() { return remboursements; }
    public void setRemboursements(List<Remboursement> remboursements) { this.remboursements = remboursements; }
}
