package com.smldb2.demo.DTO;


import java.time.LocalDateTime;

public class NotificationDTO {
    private String persoId;
    private boolean hasNotification;
    private long count;
    private String message;
    private LocalDateTime timestamp;
    private String type; // "PRESTATAIRE_UPDATE"

    public NotificationDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public NotificationDTO(String persoId, boolean hasNotification, long count, String message, String type) {
        this.persoId = persoId;
        this.hasNotification = hasNotification;
        this.count = count;
        this.message = message;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    // Getters et Setters
    public String getPersoId() {
        return persoId;
    }

    public void setPersoId(String persoId) {
        this.persoId = persoId;
    }

    public boolean isHasNotification() {
        return hasNotification;
    }

    public void setHasNotification(boolean hasNotification) {
        this.hasNotification = hasNotification;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
