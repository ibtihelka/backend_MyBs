package com.smldb2.demo.DTO;

import java.math.BigDecimal;
import java.util.List;

public class GlobalStatsDTO {
    private int totalBordereaux;
    private BigDecimal totalDepense;
    private BigDecimal totalRembourse;
    private List<BordereauSummaryDTO> bordereaux;

    public GlobalStatsDTO() {
    }

    public GlobalStatsDTO(int totalBordereaux, BigDecimal totalDepense,
                          BigDecimal totalRembourse, List<BordereauSummaryDTO> bordereaux) {
        this.totalBordereaux = totalBordereaux;
        this.totalDepense = totalDepense;
        this.totalRembourse = totalRembourse;
        this.bordereaux = bordereaux;
    }

    public int getTotalBordereaux() {
        return totalBordereaux;
    }

    public void setTotalBordereaux(int totalBordereaux) {
        this.totalBordereaux = totalBordereaux;
    }

    public BigDecimal getTotalDepense() {
        return totalDepense;
    }

    public void setTotalDepense(BigDecimal totalDepense) {
        this.totalDepense = totalDepense;
    }

    public BigDecimal getTotalRembourse() {
        return totalRembourse;
    }

    public void setTotalRembourse(BigDecimal totalRembourse) {
        this.totalRembourse = totalRembourse;
    }

    public List<BordereauSummaryDTO> getBordereaux() {
        return bordereaux;
    }

    public void setBordereaux(List<BordereauSummaryDTO> bordereaux) {
        this.bordereaux = bordereaux;
    }
}
