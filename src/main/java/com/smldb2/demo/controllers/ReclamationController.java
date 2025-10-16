package com.smldb2.demo.controllers;


import com.smldb2.demo.DTO.ReclamationDTO;
import com.smldb2.demo.Entity.Remboursement;

import com.smldb2.demo.services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reclamations")
@CrossOrigin(origins = "**")
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;

    /**
     * Créer une nouvelle réclamation
     * POST /api/reclamations
     */
    @PostMapping
    public ResponseEntity<?> createReclamation(@RequestBody ReclamationDTO reclamationDTO) {
        try {
            ReclamationDTO createdReclamation = reclamationService.createReclamation(reclamationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReclamation);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Récupérer toutes les réclamations d'un utilisateur
     * GET /api/reclamations/user/{persoId}
     */
    @GetMapping("/user/{persoId}")
    public ResponseEntity<List<ReclamationDTO>> getReclamationsByUser(@PathVariable String persoId) {
        List<ReclamationDTO> reclamations = reclamationService.getReclamationsByUser(persoId);
        return ResponseEntity.ok(reclamations);
    }

    /**
     * Récupérer une réclamation par son ID
     * GET /api/reclamations/{numReclamation}
     */
    @GetMapping("/{numReclamation}")
    public ResponseEntity<?> getReclamationById(@PathVariable Long numReclamation) {
        try {
            ReclamationDTO reclamation = reclamationService.getReclamationById(numReclamation);
            return ResponseEntity.ok(reclamation);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Récupérer les réclamations par référence de remboursement
     * GET /api/reclamations/remboursement/{refBsPhys}
     */
    @GetMapping("/remboursement/{refBsPhys}")
    public ResponseEntity<List<ReclamationDTO>> getReclamationsByRefBsPhys(@PathVariable String refBsPhys) {
        List<ReclamationDTO> reclamations = reclamationService.getReclamationsByRefBsPhys(refBsPhys);
        return ResponseEntity.ok(reclamations);
    }

    /**
     * Vérifier si une réclamation existe pour un remboursement
     * GET /api/reclamations/exists/{refBsPhys}
     */
    @GetMapping("/exists/{refBsPhys}")
    public ResponseEntity<Map<String, Boolean>> hasReclamation(@PathVariable String refBsPhys) {
        boolean exists = reclamationService.hasReclamation(refBsPhys);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupérer tous les remboursements d'un utilisateur (pour sélection)
     * GET /api/reclamations/remboursements/{persoId}
     */
    @GetMapping("/remboursements/{persoId}")
    public ResponseEntity<List<Remboursement>> getRemboursementsByUser(@PathVariable String persoId) {
        List<Remboursement> remboursements = reclamationService.getRemboursementsByUser(persoId);
        return ResponseEntity.ok(remboursements);
    }

    /**
     * Supprimer une réclamation
     * DELETE /api/reclamations/{numReclamation}
     */
    @DeleteMapping("/{numReclamation}")
    public ResponseEntity<?> deleteReclamation(@PathVariable Long numReclamation) {
        try {
            reclamationService.deleteReclamation(numReclamation);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Réclamation supprimée avec succès");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Mettre à jour la réponse d'une réclamation (pour l'admin)
     * PUT /api/reclamations/{numReclamation}/response
     */
    @PutMapping("/{numReclamation}/response")
    public ResponseEntity<?> updateReclamationResponse(
            @PathVariable Long numReclamation,
            @RequestBody Map<String, String> requestBody) {
        try {
            String response = requestBody.get("response");
            ReclamationDTO updatedReclamation = reclamationService.updateReclamationResponse(numReclamation, response);
            return ResponseEntity.ok(updatedReclamation);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Récupérer toutes les réclamations (pour l'admin)
     * GET /api/reclamations
     */
    @GetMapping
    public ResponseEntity<List<ReclamationDTO>> getAllReclamations() {
        // Cette méthode peut être implémentée dans le service si nécessaire
        return ResponseEntity.ok(List.of());
    }
}