package com.smldb2.demo.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



// 8. Entité Site
@Entity
@Table(name = "site")
public class Site {
    @Id
    @Column(name = "CodeSite")
    private String codeSite;

    @Column(name = "DES_SIT")
    private String desSit;

    @Column(name = "CodreEntre")
    private String codeEntite;

    // Constructeurs
    public Site() {}

    // Getters et Setters
    public String getCodeSite() { return codeSite; }
    public void setCodeSite(String codeSite) { this.codeSite = codeSite; }

    public String getDesSit() { return desSit; }
    public void setDesSit(String desSit) { this.desSit = desSit; }

    public String getCodeEntite() { return codeEntite; }
    public void setCodeEntite(String codeEntite) { this.codeEntite = codeEntite; }
}