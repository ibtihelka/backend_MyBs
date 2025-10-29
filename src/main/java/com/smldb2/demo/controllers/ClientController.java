package com.smldb2.demo.controllers;

import com.smldb2.demo.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
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

