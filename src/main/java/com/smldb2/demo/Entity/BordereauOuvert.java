package com.smldb2.demo.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "bordereauouvert")
public class BordereauOuvert {
    @Id
    @Column(name = "RefBorderau")
    private String refBorderau;

    @Column(name = "CodeEntre")
    private String codeEntite;

    // Constructeurs
    public BordereauOuvert() {}

    // Getters et Setters
    public String getRefBorderau() { return refBorderau; }
    public void setRefBorderau(String refBorderau) { this.refBorderau = refBorderau; }

    public String getCodeEntite() { return codeEntite; }
    public void setCodeEntite(String codeEntite) { this.codeEntite = codeEntite; }
}
