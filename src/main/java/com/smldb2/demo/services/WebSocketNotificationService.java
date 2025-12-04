package com.smldb2.demo.services;

import com.smldb2.demo.DTO.NotificationDTO;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.repositories.RemboursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketNotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RemboursementRepository remboursementRepository;

    /**
     * Envoie une notification à un utilisateur spécifique
     */
    public void sendNotificationToUser(String persoId, NotificationDTO notification) {
        messagingTemplate.convertAndSendToUser(
                persoId,
                "/queue/notifications",
                notification
        );
    }

    /**
     * Vérifie et envoie les notifications prestataire pour un utilisateur
     */
    public NotificationDTO checkAndNotifyPrestataire(String persoId) {
        List<Remboursement> remboursements = remboursementRepository.findByPersoId(persoId);

        long count = remboursements.stream()
                .filter(r -> r.getCodDoctCv() != null && !r.getCodDoctCv().trim().isEmpty())
                .count();

        boolean hasNotification = count > 0;
        String message = hasNotification
                ? String.format("Vous avez %d prestataire(s) avec des documents disponibles", count)
                : "Aucune notification";

        NotificationDTO notification = new NotificationDTO(
                persoId,
                hasNotification,
                count,
                message,
                "PRESTATAIRE_UPDATE"
        );

        return notification;
    }

    /**
     * Envoie une notification broadcast à tous les utilisateurs
     */
    public void broadcastNotification(NotificationDTO notification) {
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    /**
     * Notifie un utilisateur spécifique d'un changement de prestataire
     */
    public void notifyPrestataireUpdate(String persoId) {
        NotificationDTO notification = checkAndNotifyPrestataire(persoId);
        sendNotificationToUser(persoId, notification);
    }
}