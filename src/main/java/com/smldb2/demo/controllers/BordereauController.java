package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.BordereauStatsDTO;
import com.smldb2.demo.DTO.GlobalStatsDTO;
import com.smldb2.demo.Entity.Bordereau;
import com.smldb2.demo.services.BordereauService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bordereaux")
@CrossOrigin(origins = "**")
public class BordereauController {

    private final BordereauService bordereauService;

    public BordereauController(BordereauService bordereauService) {
        this.bordereauService = bordereauService;
    }

    /**
     * Récupérer tous les bordereaux
     */
    @GetMapping
    public ResponseEntity<List<Bordereau>> getAllBordereaux() {
        return ResponseEntity.ok(bordereauService.getAllBordereaux());
    }

    /**
     * Récupérer les bordereaux par préfixe (STAFIM, TP, etc.)
     */
    @GetMapping("/by-prefix")
    public ResponseEntity<List<Bordereau>> getBordereauxByPrefix(
            @RequestParam String prefix) {
        return ResponseEntity.ok(bordereauService.getBordereauxByPrefix(prefix));
    }

    /**
     * Récupérer un bordereau avec ses remboursements
     */
    @GetMapping("/{refBordereau}")
    public ResponseEntity<Bordereau> getBordereauAvecRemboursements(
            @PathVariable String refBordereau) {
        Optional<Bordereau> bordereau = bordereauService
                .getBordereauAvecRemboursements(refBordereau);
        return bordereau.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 📊 NOUVEAU: Statistiques globales de tous les bordereaux (ou par préfixe)
     */
    @GetMapping("/stats/global")
    public ResponseEntity<GlobalStatsDTO> getGlobalStats(
            @RequestParam(required = false) String prefix) {
        return ResponseEntity.ok(bordereauService.getGlobalBordereauxStats(prefix));
    }

    /**
     * 📊 NOUVEAU: Statistiques détaillées d'un bordereau spécifique
     */
    @GetMapping("/stats/{refBordereau}")
    public ResponseEntity<BordereauStatsDTO> getBordereauStats(
            @PathVariable String refBordereau) {
        try {
            BordereauStatsDTO stats = bordereauService.getBordereauStats(refBordereau);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}