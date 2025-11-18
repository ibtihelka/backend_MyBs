package com.smldb2.demo.DTO;

public class StatistiquesPrestataireDTO {

    private int nombreAdherents;
    private int frequenceAdherents;  // Nombre de remboursements des adhérents eux-mêmes
    private int enfant01;
    private int enfant02;
    private int enfant03;
    private int enfant04;
    private int enfant05;
    private int conjoint98;
    private int conjoint99;

    // Constructeur vide
    public StatistiquesPrestataireDTO() {}

    // Constructeur avec tous les paramètres
    public StatistiquesPrestataireDTO(int nombreAdherents, int frequenceAdherents,
                                      int enfant01, int enfant02, int enfant03,
                                      int enfant04, int enfant05,
                                      int conjoint98, int conjoint99) {
        this.nombreAdherents = nombreAdherents;
        this.frequenceAdherents = frequenceAdherents;
        this.enfant01 = enfant01;
        this.enfant02 = enfant02;
        this.enfant03 = enfant03;
        this.enfant04 = enfant04;
        this.enfant05 = enfant05;
        this.conjoint98 = conjoint98;
        this.conjoint99 = conjoint99;
    }

    // Getters et Setters
    public int getNombreAdherents() {
        return nombreAdherents;
    }

    public void setNombreAdherents(int nombreAdherents) {
        this.nombreAdherents = nombreAdherents;
    }

    public int getFrequenceAdherents() {
        return frequenceAdherents;
    }

    public void setFrequenceAdherents(int frequenceAdherents) {
        this.frequenceAdherents = frequenceAdherents;
    }

    public int getEnfant01() {
        return enfant01;
    }

    public void setEnfant01(int enfant01) {
        this.enfant01 = enfant01;
    }

    public int getEnfant02() {
        return enfant02;
    }

    public void setEnfant02(int enfant02) {
        this.enfant02 = enfant02;
    }

    public int getEnfant03() {
        return enfant03;
    }

    public void setEnfant03(int enfant03) {
        this.enfant03 = enfant03;
    }

    public int getEnfant04() {
        return enfant04;
    }

    public void setEnfant04(int enfant04) {
        this.enfant04 = enfant04;
    }

    public int getEnfant05() {
        return enfant05;
    }

    public void setEnfant05(int enfant05) {
        this.enfant05 = enfant05;
    }

    public int getConjoint98() {
        return conjoint98;
    }

    public void setConjoint98(int conjoint98) {
        this.conjoint98 = conjoint98;
    }

    public int getConjoint99() {
        return conjoint99;
    }

    public void setConjoint99(int conjoint99) {
        this.conjoint99 = conjoint99;
    }
}