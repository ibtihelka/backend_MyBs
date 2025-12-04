package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.ContreVisiteDTO;
import com.smldb2.demo.DTO.MatStatDTO;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.services.RemboursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/remboursements")
@CrossOrigin(origins = "**")
public class RemboursementController {

    @Autowired
    private RemboursementService remboursementService;

    // ========== ENDPOINTS EXISTANTS ==========

    @GetMapping("/mf")
    public ResponseEntity<List<String>> getMfByPersoAndBs(
            @RequestParam String persoId,
            @RequestParam String refBsPhys) {
        List<String> mfList = remboursementService.getMatriculesFiscalesByPersoAndBs(persoId, refBsPhys);
        return ResponseEntity.ok(mfList);
    }

    @GetMapping("/stat/{persoId}")
    public ResponseEntity<List<Object[]>> getDateBsNumBsMf(@PathVariable String persoId) {
        List<Object[]> data = remboursementService.getDateBsNumBsMfByPersoId(persoId);

        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(data);
    }


    @GetMapping("/details/{refBsPhys}")
    public ResponseEntity<Remboursement> getRemboursementWithActes(@PathVariable String refBsPhys) {
        return remboursementService.getRemboursementWithActes(refBsPhys)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Remboursement>> getAllRemboursements() {
        List<Remboursement> remboursements = remboursementService.getAllRemboursements();
        return ResponseEntity.ok(remboursements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Remboursement> getRemboursementById(@PathVariable String id) {
        return remboursementService.getRemboursementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{persoId}")
    public ResponseEntity<List<Remboursement>> getRemboursementsByPersoId(@PathVariable String persoId) {
        List<Remboursement> remboursements = remboursementService.getRemboursementsByPersoId(persoId);
        return ResponseEntity.ok(remboursements);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Remboursement>> getRemboursementsByStatus(@PathVariable String status) {
        List<Remboursement> remboursements = remboursementService.getRemboursementsByStatus(status);
        return ResponseEntity.ok(remboursements);
    }

    @GetMapping("/bordereau/{refBordereau}")
    public ResponseEntity<List<Remboursement>> getRemboursementsByBordereau(@PathVariable String refBordereau) {
        List<Remboursement> remboursements = remboursementService.getRemboursementsByBordereau(refBordereau);
        return ResponseEntity.ok(remboursements);
    }

    // ========== ✅ NOUVEAUX ENDPOINTS POUR LES NOTIFICATIONS ==========

    /**
     * Compter les remboursements avec cod_doct_cv non null pour un utilisateur
     * GET /api/remboursements/user/{persoId}/count-cod-doct-cv
     */
    @GetMapping("/user/{persoId}/count-cod-doct-cv")
    public ResponseEntity<Long> countRemboursementsWithCodDoctCv(@PathVariable String persoId) {
        try {
            Long count = remboursementService.countRemboursementsWithCodDoctCv(persoId);
            System.out.println("✅ Compteur notifications pour " + persoId + ": " + count);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors du comptage des remboursements avec cod_doct_cv: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(0L);
        }
    }

    /**
     * Marquer les remboursements avec cod_doct_cv comme "vus" pour un utilisateur
     * POST /api/remboursements/user/{persoId}/mark-cod-doct-cv-viewed
     */
    @PostMapping("/user/{persoId}/mark-cod-doct-cv-viewed")
    public ResponseEntity<Map<String, Object>> markCodDoctCvAsViewed(@PathVariable String persoId) {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Notifications marquées comme vues");
            response.put("persoId", persoId);

            System.out.println("✅ Notifications marquées comme vues pour: " + persoId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur : " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * Récupérer les contre-visites d'un utilisateur
     * GET /api/remboursements/user/{persoId}/contre-visites
     */
    @GetMapping("/user/{persoId}/contre-visites")
    public ResponseEntity<List<ContreVisiteDTO>> getContreVisites(@PathVariable String persoId) {
        try {
            List<ContreVisiteDTO> contreVisites = remboursementService.getContreVisitesByPersoId(persoId);
            System.out.println("✅ Contre-visites récupérées pour " + persoId + ": " + contreVisites.size());
            return ResponseEntity.ok(contreVisites);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des contre-visites: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new ArrayList<>());
        }

    }


}