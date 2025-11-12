package com.smldb2.demo.services;

import com.smldb2.demo.Entity.Client;
import com.smldb2.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Récupère le numéro de contrat si le codeClt correspond au persoId
     * @param codeClt Code client (doit correspondre au persoId)
     * @param persoId ID de la personne (pour vérification)
     * @return Le numéro de contrat ou null si non trouvé
     */
    public String getNumContratIfMatch(String codeClt, String persoId) {
        // Vérifier que codeClt = persoId
        if (codeClt == null || persoId == null || !codeClt.equals(persoId)) {
            System.err.println("❌ CodeClt et PersoId ne correspondent pas: " + codeClt + " != " + persoId);
            return null;
        }

        // Récupérer le client par codeClt
        Optional<Client> clientOpt = clientRepository.findById(codeClt);

        if (clientOpt.isPresent()) {
            String numContrat = clientOpt.get().getNumContrat();
            System.out.println("✅ Numéro de contrat trouvé pour " + codeClt + ": " + numContrat);
            return numContrat;
        } else {
            System.err.println("❌ Client non trouvé pour codeClt: " + codeClt);
            return null;
        }
    }

    /**
     * Récupère directement le numéro de contrat par codeClt
     */
    public String getNumContratByCodeClt(String codeClt) {
        return clientRepository.findById(codeClt)
                .map(Client::getNumContrat)
                .orElse(null);
    }
}