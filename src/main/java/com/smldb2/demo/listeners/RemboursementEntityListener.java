package com.smldb2.demo.listeners;

import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.services.WebSocketNotificationService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Écoute les changements sur les entités Remboursement
 * et envoie automatiquement des notifications WebSocket
 */
@Component
public class RemboursementEntityListener {

    private static WebSocketNotificationService notificationService;

    @Autowired
    public void setNotificationService(WebSocketNotificationService service) {
        RemboursementEntityListener.notificationService = service;
    }

    @PostPersist
    @PostUpdate
    public void onRemboursementChange(Remboursement remboursement) {
        if (remboursement.getCodDoctCv() != null &&
                !remboursement.getCodDoctCv().trim().isEmpty()) {

            System.out.println("🔔 Changement détecté - codDoctCv présent pour: " +
                    remboursement.getPersoId());

            if (notificationService != null) {
                notificationService.notifyPrestataireUpdate(remboursement.getPersoId());
            }
        }
    }
}

// Ensuite, ajoutez ceci dans votre entité Remboursement.java:
// @EntityListeners(RemboursementEntityListener.class)
// public class Remboursement {
//    ...
// }