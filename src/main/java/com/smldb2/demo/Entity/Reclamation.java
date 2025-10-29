package com.smldb2.demo.Entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


// 10. Entité Reclamation
@Entity
@Table(name = "reclamations")
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NumReclamation")
    private Long numReclamation;

    @Column(name = "REF_BS_PHYS")
    private String refBsPhys;



    @Column(name = "PERSO_ID", insertable = false, updatable = false)
    private String persoId;


    @Column(name = "TitreReclamation")
    private String titreReclamation;

    @Column(name = "TexteReclamation" , columnDefinition = "TEXT")

    private String texteReclamation;

    @Column(name = "ReponseRec", columnDefinition = "TEXT")

    private String responseRec;

    @Column(name = "exported")
    private String exported;

    @Column(name = "dateCreation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    // Relation avec User
    @ManyToOne
    @JoinColumn(name = "PERSO_ID")
    @JsonBackReference("user-reclamations")
    private User user;

    // Constructeurs
    public Reclamation() {}

    // Getters et Setters

    public Long getNumReclamation() {
        return numReclamation;
    }

    public void setNumReclamation(Long numReclamation) {
        this.numReclamation = numReclamation;
    }

    public String getRefBsPhys() { return refBsPhys; }
    public void setRefBsPhys(String refBsPhys) { this.refBsPhys = refBsPhys; }

    public String getPersoId() { return persoId; }
    public void setPersoId(String persoId) { this.persoId = persoId; }

    public String getTitreReclamation() { return titreReclamation; }
    public void setTitreReclamation(String titreReclamation) { this.titreReclamation = titreReclamation; }

    public String getTexteReclamation() { return texteReclamation; }
    public void setTexteReclamation(String texteReclamation) { this.texteReclamation = texteReclamation; }

    public String getResponseRec() { return responseRec; }
    public void setResponseRec(String responseRec) { this.responseRec = responseRec; }

    public String getExported() { return exported; }
    public void setExported(String exported) { this.exported = exported; }

    public Date getDateCreation() { return dateCreation; }
    public void setDateCreation(Date dateCreation) { this.dateCreation = dateCreation; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}