package com.smldb2.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "rapport_contre_visite")
public class RapportContreVisite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRESTATAIRE_ID")
    private String prestataireId;

    @Column(name = "BENEFICIAIRE_ID")
    private String beneficiaireId; // User ID ou Famille prenom

    @Column(name = "BENEFICIAIRE_NOM")
    private String beneficiaireNom;

    @Column(name = "TYPE_BENEFICIAIRE")
    private String typeBeneficiaire; // "USER" ou "FAMILLE"

    @Column(name = "REF_BS_PHYS")
    private String refBsPhys;

    @Column(name = "TYPE_RAPPORT")
    private String typeRapport; // "DENTISTE" ou "OPTICIEN"

    @Column(name = "OBSERVATION", length = 1000)
    private String observation;

    @Column(name = "DATE_RAPPORT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRapport;

    // Champs spécifiques DENTISTE (tableau JSON ou texte structuré)
    @Column(name = "LIGNES_DENTAIRE", columnDefinition = "TEXT")
    private String lignesDentaire; // Format JSON: [{dent, codeActe, cotation, avisMedical}, ...]

    // Champs spécifiques OPTICIEN
    @Column(name = "ACUITE_VISUELLE_OD")
    private String acuiteVisuelleOD;

    @Column(name = "ACUITE_VISUELLE_OG")
    private String acuiteVisuelleOG;

    @Column(name = "PRIX_MONTURE")
    private BigDecimal prixMonture;

    @Column(name = "NATURE_VERRES")
    private String natureVerres;

    @Column(name = "PRIX_VERRES")
    private BigDecimal prixVerres;

    @PrePersist
    protected void onCreate() {
        dateRapport = new Date();
    }

    // Constructeurs
    public RapportContreVisite() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPrestataireId() { return prestataireId; }
    public void setPrestataireId(String prestataireId) { this.prestataireId = prestataireId; }

    public String getBeneficiaireId() { return beneficiaireId; }
    public void setBeneficiaireId(String beneficiaireId) { this.beneficiaireId = beneficiaireId; }

    public String getBeneficiaireNom() { return beneficiaireNom; }
    public void setBeneficiaireNom(String beneficiaireNom) { this.beneficiaireNom = beneficiaireNom; }

    public String getTypeBeneficiaire() { return typeBeneficiaire; }
    public void setTypeBeneficiaire(String typeBeneficiaire) { this.typeBeneficiaire = typeBeneficiaire; }

    public String getRefBsPhys() { return refBsPhys; }
    public void setRefBsPhys(String refBsPhys) { this.refBsPhys = refBsPhys; }

    public String getTypeRapport() { return typeRapport; }
    public void setTypeRapport(String typeRapport) { this.typeRapport = typeRapport; }

    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }

    public Date getDateRapport() { return dateRapport; }
    public void setDateRapport(Date dateRapport) { this.dateRapport = dateRapport; }

    public String getLignesDentaire() { return lignesDentaire; }
    public void setLignesDentaire(String lignesDentaire) { this.lignesDentaire = lignesDentaire; }

    public String getAcuiteVisuelleOD() { return acuiteVisuelleOD; }
    public void setAcuiteVisuelleOD(String acuiteVisuelleOD) { this.acuiteVisuelleOD = acuiteVisuelleOD; }

    public String getAcuiteVisuelleOG() { return acuiteVisuelleOG; }
    public void setAcuiteVisuelleOG(String acuiteVisuelleOG) { this.acuiteVisuelleOG = acuiteVisuelleOG; }

    public BigDecimal getPrixMonture() { return prixMonture; }
    public void setPrixMonture(BigDecimal prixMonture) { this.prixMonture = prixMonture; }

    public String getNatureVerres() { return natureVerres; }
    public void setNatureVerres(String natureVerres) { this.natureVerres = natureVerres; }

    public BigDecimal getPrixVerres() { return prixVerres; }
    public void setPrixVerres(BigDecimal prixVerres) { this.prixVerres = prixVerres; }
}