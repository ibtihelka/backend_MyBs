package com.smldb2.demo.DTO;

public class UserStatsDTO {
    private Long total;
    private Long nouveaux;

    // Constructeur par défaut
    public UserStatsDTO() {
    }

    // Constructeur avec paramètres
    // ATTENTION: L'ordre doit être cohérent avec l'appel dans UserService
    public UserStatsDTO(Long total, Long nouveaux) {
        this.total = total;
        this.nouveaux = nouveaux;
    }

    // Getters et Setters
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getNouveaux() {
        return nouveaux;
    }

    public void setNouveaux(Long nouveaux) {
        this.nouveaux = nouveaux;
    }

    @Override
    public String toString() {
        return "UserStatsDTO{" +
                "total=" + total +
                ", nouveaux=" + nouveaux +
                '}';
    }
}