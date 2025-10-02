package com.smldb2.demo.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
// 3. Entité Bssaisis
@Entity
@Table(name = "bssaisis")
public class Bssaisis {
    @Id
    @Column(name = "RefBS")
    private String refBs;

    @Column(name = "montant")
    private BigDecimal montant;

    @Column(name = "Code_Site")
    private String codeSite;

    @Column(name = "Code_Entreprise")
    private String codeEntreprise;

    @Column(name = "RefBordereau")
    private String refBordereau;

    @Column(name = "DateBS")
    private Date dateBS;

    private String matricule;
    private String nom;




    // Constructeurs
    public Bssaisis() {}

    // Getters et Setters
    public String getRefBs() { return refBs; }
    public void setRefBs(String refBs) { this.refBs = refBs; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public String getRefBordereau() { return refBordereau; }
    public void setRefBordereau(String refBordereau) { this.refBordereau = refBordereau; }

    public String getCodeSite() { return codeSite; }
    public void setCodeSite(String codeSite) { this.codeSite = codeSite; }

    public String getCodeEntreprise() { return codeEntreprise; }
    public void setCodeEntreprise(String codeEntreprise) { this.codeEntreprise = codeEntreprise; }

    public Date getDateBS() { return dateBS; }
    public void setDateBS(Date dateBS) { this.dateBS = dateBS; }

    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}