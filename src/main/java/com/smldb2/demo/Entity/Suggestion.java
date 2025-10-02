package com.smldb2.demo.Entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "suggestions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NumSuggestion") // EXACT comme dans la base
    @JsonProperty("numSuggestion")
    private Long numSuggestion;

    @Column(name = "TitreSuggestion") // EXACT comme dans la base
    @JsonProperty("titreSuggestion")
    private String titreSuggestion;

    @Column(name = "TexteSuggestion") // EXACT comme dans la base
    @JsonProperty("texteSuggestion")
    private String texteSuggestion;

    @Column(name = "perso_id") // EXACT comme dans la base
    @JsonProperty("persoId")
    private String persoId;

    @Column(name = "exported") // EXACT comme dans la base
    @JsonProperty("exported")
    private String exported;

    @Column(name = "DateCreation") // EXACT comme dans la base
    @JsonProperty("dateCreation")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dateCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perso_id", referencedColumnName = "PERSO_ID", insertable = false, updatable = false)
    @JsonBackReference
    private User user;

    // Constructeurs
    public Suggestion() {}

    public Suggestion(String titreSuggestion, String texteSuggestion, String persoId) {
        this.titreSuggestion = titreSuggestion;
        this.texteSuggestion = texteSuggestion;
        this.persoId = persoId;
        this.dateCreation = new Timestamp(System.currentTimeMillis());
    }

    // Getters et Setters
    public Long getNumSuggestion() {
        return numSuggestion;
    }

    public void setNumSuggestion(Long numSuggestion) {
        this.numSuggestion = numSuggestion;
    }

    public String getTitreSuggestion() {
        return titreSuggestion;
    }

    public void setTitreSuggestion(String titreSuggestion) {
        this.titreSuggestion = titreSuggestion;
    }

    public String getTexteSuggestion() {
        return texteSuggestion;
    }

    public void setTexteSuggestion(String texteSuggestion) {
        this.texteSuggestion = texteSuggestion;
    }

    public String getPersoId() {
        return persoId;
    }

    public void setPersoId(String persoId) {
        this.persoId = persoId;
    }

    public String getExported() {
        return exported;
    }

    public void setExported(String exported) {
        this.exported = exported;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "numSuggestion=" + numSuggestion +
                ", titreSuggestion='" + titreSuggestion + '\'' +
                ", texteSuggestion='" + texteSuggestion + '\'' +
                ", persoId='" + persoId + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}