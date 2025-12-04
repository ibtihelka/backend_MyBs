package com.smldb2.demo.DTO;

import java.math.BigDecimal;

public class StatMfDTO {

    private String mf;
    private Long nombreRemboursements;
    private BigDecimal totalRembourse;

    public StatMfDTO(String mf, Long nombreRemboursements, BigDecimal totalRembourse) {
        this.mf = mf;
        this.nombreRemboursements = nombreRemboursements;
        this.totalRembourse = totalRembourse;
    }

    // getters et setters
    public String getMf() { return mf; }
    public void setMf(String mf) { this.mf = mf; }

    public Long getNombreRemboursements() { return nombreRemboursements; }
    public void setNombreRemboursements(Long nombreRemboursements) { this.nombreRemboursements = nombreRemboursements; }

    public BigDecimal getTotalRembourse() { return totalRembourse; }
    public void setTotalRembourse(BigDecimal totalRembourse) { this.totalRembourse = totalRembourse; }
}
