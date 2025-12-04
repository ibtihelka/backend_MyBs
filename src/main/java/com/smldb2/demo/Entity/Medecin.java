package com.smldb2.demo.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medecin")
public class Medecin {

    @Id
    @Column(name = "COD_DOCT")
    private String codDoct;

    @Column(name = "DES")
    private String des;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "ADRESS")
    private String adress;

    @Column(name = "VILLE")
    private String ville;

    @Column(name = "MF")
    private String mf;

    public Medecin() {}

    // --- GETTERS & SETTERS ---

    public String getCodDoct() {
        return codDoct;
    }
    public void setCodDoct(String codDoct) {
        this.codDoct = codDoct;
    }

    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMf() {
        return mf;
    }
    public void setMf(String mf) {
        this.mf = mf;
    }
}
