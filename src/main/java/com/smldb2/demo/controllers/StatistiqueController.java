package com.smldb2.demo.controllers;
//medecin


import com.smldb2.demo.DTO.StatMedecinActeDTO;

import com.smldb2.demo.services.StatistiqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistiques")
@CrossOrigin(origins = "http://localhost:4200")
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    public StatistiqueController(StatistiqueService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }

    @GetMapping("/top-medecin-par-acte")
    public ResponseEntity<List<StatMedecinActeDTO>> getTopMedecinParActe(
            @RequestParam String codeSociete) {
        List<StatMedecinActeDTO> stats = statistiqueService.getTopMedecinParActe(codeSociete);
        return ResponseEntity.ok(stats);
    }


    // CHANGEMENT ICI : Nouvelle URL pour éviter le conflit
    @GetMapping("/liste-societes")
    public ResponseEntity<List<String>> getAllSocietes() {
        List<String> societes = statistiqueService.getAllCodeSociete();
        return ResponseEntity.ok(societes);
    }
}
