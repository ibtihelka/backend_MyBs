package com.smldb2.demo.services;

import com.opencsv.CSVReader;
import com.smldb2.demo.Entity.Client;
import com.smldb2.demo.repositories.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.util.*;

@Service
public class CsvService {

    private final ClientRepository clientRepository;

    @Value("${app.csv.path}")
    private String csvPath;

    public CsvService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void importCsv() {
        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            String[] line;
            reader.readNext(); // ignorer l'entête

            Set<String> csvCodes = new HashSet<>();

            while ((line = reader.readNext()) != null) {
                String codeClt = line[0].trim();
                String description = line[1].trim();
                String numContrat = line[2].trim();

                csvCodes.add(codeClt);

                Optional<Client> optionalClient = clientRepository.findById(codeClt);

                Client client;
                if (optionalClient.isPresent()) {
                    client = optionalClient.get();
                    client.setDescription(description);
                    client.setNumContrat(numContrat);
                } else {
                    client = new Client();
                    client.setCodeClt(codeClt);
                    client.setDescription(description);
                    client.setNumContrat(numContrat);
                }

                clientRepository.save(client);
            }

            // Supprimer les clients qui ne sont plus dans le CSV
            List<Client> allClients = clientRepository.findAll();
            for (Client client : allClients) {
                if (!csvCodes.contains(client.getCodeClt())) {
                    clientRepository.delete(client);
                    System.out.println("🗑️ Client supprimé : " + client.getCodeClt());
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'import du CSV : " + e.getMessage());
            e.printStackTrace();
        }
    }
}