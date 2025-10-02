package com.smldb2.demo.DTO;

public class UserGlobalStatsDTO {
    private int total;
    private int nouveaux;

    public UserGlobalStatsDTO() {
    }

    public UserGlobalStatsDTO(int total, int nouveaux) {
        this.total = total;
        this.nouveaux = nouveaux;
    }

    // Getters and Setters
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNouveaux() {
        return nouveaux;
    }

    public void setNouveaux(int nouveaux) {
        this.nouveaux = nouveaux;
    }

    @Override
    public String toString() {
        return "UserGlobalStatsDTO{" +
                "total=" + total +
                ", nouveaux=" + nouveaux +
                '}';
    }
}