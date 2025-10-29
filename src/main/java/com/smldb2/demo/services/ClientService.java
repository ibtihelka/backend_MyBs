package com.smldb2.demo.services;

import com.smldb2.demo.Entity.Client;
import com.smldb2.demo.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public String getNumContratIfMatch(String codeClt, String persoId) {
        // On cherche juste le client par codeClt
        Optional<Client> clientOpt = clientRepository.findByCodeClt(codeClt);

        if (clientOpt.isPresent()) {
            Client client = clientOpt.get();
            // Comparer le persoId uniquement si vous avez une relation User <-> Client
            return client.getNumContrat();
        }

        return null;
    }

}
