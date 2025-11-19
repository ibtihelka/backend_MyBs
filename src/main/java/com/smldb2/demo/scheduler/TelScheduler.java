package com.smldb2.demo.scheduler;

import com.smldb2.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Scheduler pour traiter automatiquement les demandes de modification de téléphone
 * S'exécute tous les jours à 11h10
 */
@Component
public class TelScheduler {

    @Autowired
    private UserService userService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Traitement automatique des téléphones en attente
     * Cron: 0 10 11 * * ? => Tous les jours à 11h10
     */
    @Scheduled(cron = "0 10 11 * * ?")
    public void processPendingTels() {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("\n\n");
        System.out.println("████████████████████████████████████████");
        System.out.println("📱 SCHEDULER TÉLÉPHONE - EXÉCUTION À 11H10");
        System.out.println("📅 " + timestamp);
        System.out.println("████████████████████████████████████████");

        try {
            userService.traiterDemandesTelEnAttente();
        } catch (Exception e) {
            System.err.println("❌❌❌ ERREUR CRITIQUE SCHEDULER ❌❌❌");
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("████████████████████████████████████████");
        System.out.println("🏁 FIN DE L'EXÉCUTION DU SCHEDULER");
        System.out.println("████████████████████████████████████████");
        System.out.println("\n\n");
    }
}