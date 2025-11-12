package com.smldb2.demo.controllers;

import com.smldb2.demo.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@CrossOrigin(origins = "**") // Ajoutez cette ligne
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/numContrat")
    public ResponseEntity<String> getNumContrat(
            @RequestParam String codeClt,
            @RequestParam String persoId) {

        String numContrat = clientService.getNumContratIfMatch(codeClt, persoId);
        if (numContrat != null) {
            return ResponseEntity.ok(numContrat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}