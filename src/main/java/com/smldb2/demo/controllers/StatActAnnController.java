package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.ReportingAnnuelDTO;
import com.smldb2.demo.services.StatActAnnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistiques-annuelles")
@CrossOrigin(origins = "**")
public class StatActAnnController {

    @Autowired
    private StatActAnnService statActAnnService;

    /**
     * GET /api/statistiques-annuelles/annees
     * Récupère la liste des années disponibles
     */
    @GetMapping("/annees")
    public ResponseEntity<List<Integer>> getAnnees() {
        List<Integer> annees = statActAnnService.getAllAnnees();
        return ResponseEntity.ok(annees);
    }

    /**
     * GET /api/statistiques-annuelles/societes
     * Récupère la liste des sociétés disponibles
     */
    @GetMapping("/societes")
    public ResponseEntity<List<String>> getSocietes() {
        List<String> societes = statActAnnService.getAllSocietes();
        return ResponseEntity.ok(societes);
    }

    /**
     * GET /api/statistiques-annuelles/reporting
     * Récupère le reporting annuel pour une société et une année
     */
    @GetMapping("/reporting")
    public ResponseEntity<ReportingAnnuelDTO> getReportingAnnuel(
            @RequestParam Integer annee,
            @RequestParam String codeSociete) {

        ReportingAnnuelDTO reporting = statActAnnService
                .getReportingAnnuel(annee, codeSociete);

        return ResponseEntity.ok(reporting);
    }
}