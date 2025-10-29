package com.smldb2.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;

@Component
public class CsvWatcher {

    private final CsvService csvService;

    @Value("${app.csv.path}")
    private String csvPath;

    public CsvWatcher(CsvService csvService) {
        this.csvService = csvService;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Async
    public void watchCsvFile() {
        try {
            Path csvFile = Paths.get(csvPath);
            Path directory = csvFile.getParent();
            String fileName = csvFile.getFileName().toString();

            WatchService watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_CREATE);

            System.out.println(" Surveillance du fichier : " + csvPath);

            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    System.err.println("Service de surveillance interrompu");
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.context().toString().equals(fileName)) {
                        System.out.println("📁 Fichier CSV modifié, mise à jour de la base...");

                        // Petite pause pour éviter les lectures multiples
                        Thread.sleep(500);

                        csvService.importCsv();
                        System.out.println("✅ Base de données mise à jour");
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    System.err.println("Le répertoire surveillé n'est plus accessible");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la surveillance du fichier : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace();
        }
    }
}