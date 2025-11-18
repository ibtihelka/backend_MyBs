package com.smldb2.demo.controllers;


import com.smldb2.demo.DTO.ActeDetailDTO;
import com.smldb2.demo.DTO.ReportingResponseDTO;
import com.smldb2.demo.services.StatActDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/statistiques")
@CrossOrigin(origins = "**")
public class StatActDateController {

    @Autowired
    private StatActDateService statActDateService;

    /**
     * GET /api/statistiques/societes
     * Récupère la liste des codes société disponibles
     */
    @GetMapping("/societes")
    public ResponseEntity<List<String>> getSocietes() {
        List<String> societes = statActDateService.getAllSocietes();
        return ResponseEntity.ok(societes);
    }

    /**
     * GET /api/statistiques/reporting
     * Récupère le reporting complet pour une société et une période
     */
    @GetMapping("/reporting")
    public ResponseEntity<ReportingResponseDTO> getReporting(
            @RequestParam String codeSociete,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        ReportingResponseDTO reporting = statActDateService
                .getReportingBySocieteAndPeriode(codeSociete, dateDebut, dateFin);

        return ResponseEntity.ok(reporting);
    }

    /**
     * GET /api/statistiques/adherents
     * Récupère la liste des matricules avec remboursements
     */
    @GetMapping("/adherents")
    public ResponseEntity<List<String>> getAdherents(
            @RequestParam String codeSociete,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        List<String> matricules = statActDateService
                .getMatriculesBySocieteAndPeriode(codeSociete, dateDebut, dateFin);

        return ResponseEntity.ok(matricules);
    }

    /**
     * GET /api/statistiques/adherents/{matricule}/actes
     * Récupère les actes détaillés d'un adhérent
     */
    @GetMapping("/adherents/{matricule}/actes")
    public ResponseEntity<List<ActeDetailDTO>> getActesByAdherent(
            @PathVariable String matricule,
            @RequestParam String codeSociete,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        List<ActeDetailDTO> actes = statActDateService
                .getActesByMatricule(matricule, codeSociete, dateDebut, dateFin);

        return ResponseEntity.ok(actes);
    }
}
