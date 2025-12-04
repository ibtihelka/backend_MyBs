package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.NotificationDTO;
import com.smldb2.demo.services.RemboursementService;
import com.smldb2.demo.services.WebSocketNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "**")
public class NotificationController {

    @Autowired
    private RemboursementService remboursementService;

    @Autowired
    private WebSocketNotificationService webSocketNotificationService;

    /**
     * REST Endpoint - Vérifie s'il existe des remboursements avec codDoctCv
     */
    @GetMapping("/prestataire/{persoId}")
    public ResponseEntity<Map<String, Object>> checkPrestataireNotification(@PathVariable String persoId) {
        long count = remboursementService.getRemboursementsByPersoId(persoId)
                .stream()
                .filter(r -> r.getCodDoctCv() != null && !r.getCodDoctCv().trim().isEmpty())
                .count();

        Map<String, Object> response = new HashMap<>();
        response.put("hasNotification", count > 0);
        response.put("count", count);
        response.put("message", count > 0
                ? String.format("%d prestataire(s) avec documents", count)
                : "Aucune notification");

        return ResponseEntity.ok(response);
    }

    /**
     * WebSocket - L'utilisateur s'abonne aux notifications
     */
    @MessageMapping("/subscribe")
    @SendToUser("/queue/notifications")
    public NotificationDTO subscribeToNotifications(String persoId) {
        System.out.println("📡 Utilisateur abonné: " + persoId);
        return webSocketNotificationService.checkAndNotifyPrestataire(persoId);
    }

    /**
     * WebSocket - Demande manuelle de vérification
     */
    @MessageMapping("/check")
    @SendToUser("/queue/notifications")
    public NotificationDTO checkNotifications(String persoId) {
        System.out.println("🔍 Vérification demandée pour: " + persoId);
        return webSocketNotificationService.checkAndNotifyPrestataire(persoId);
    }

    /**
     * REST Endpoint - Force l'envoi d'une notification (pour tests/admin)
     */
    @PostMapping("/send/{persoId}")
    public ResponseEntity<String> sendNotification(@PathVariable String persoId) {
        webSocketNotificationService.notifyPrestataireUpdate(persoId);
        return ResponseEntity.ok("Notification envoyée à " + persoId);
    }
}