package com.smldb2.demo.controllers;


import com.smldb2.demo.services.ActeAffectationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/actes/affectation")
@CrossOrigin(origins = "*")
public class ActeAffectationController {

    @Autowired
    private ActeAffectationService affectationService;

    /**
     * Affecter tous les actes existants à leurs remboursements
     * POST http://localhost:8080/api/actes/affectation/tous
     */
    @PostMapping("/tous")
    public ResponseEntity<Map<String, Object>> affecterTousLesActes() {
        try {
            Map<String, Object> resultat = affectationService.affecterTousLesActes();
            return ResponseEntity.ok(resultat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de l'affectation: " + e.getMessage()));
        }
    }

    /**
     * Affecter les actes d'un remboursement spécifique
     * POST http://localhost:8080/api/actes/affectation/remboursement/{refBsPhys}
     */
    @PostMapping("/remboursement/{refBsPhys}")
    public ResponseEntity<Map<String, Object>> affecterActesPourRemboursement(
            @PathVariable String refBsPhys) {
        try {
            Map<String, Object> resultat = affectationService.affecterActesPourRemboursement(refBsPhys);

            if (resultat.containsKey("erreur")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultat);
            }

            return ResponseEntity.ok(resultat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de l'affectation: " + e.getMessage()));
        }
    }

    /**
     * Affecter un acte unique à un remboursement
     * POST http://localhost:8080/api/actes/affectation/acte/{idActe}/remboursement/{refBsPhys}
     */
    @PostMapping("/acte/{idActe}/remboursement/{refBsPhys}")
    public ResponseEntity<Map<String, Object>> affecterActeUnique(
            @PathVariable Long idActe,
            @PathVariable String refBsPhys) {
        try {
            Map<String, Object> resultat = affectationService.affecterActeUnique(idActe, refBsPhys);

            if (resultat.containsKey("erreur")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultat);
            }

            return ResponseEntity.ok(resultat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de l'affectation: " + e.getMessage()));
        }
    }

    /**
     * Vérifier l'état d'affectation des actes
     * GET http://localhost:8080/api/actes/affectation/etat
     */
    @GetMapping("/etat")
    public ResponseEntity<Map<String, Object>> verifierEtatAffectation() {
        try {
            Map<String, Object> etat = affectationService.verifierEtatAffectation();
            return ResponseEntity.ok(etat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de la vérification: " + e.getMessage()));
        }
    }

    /**
     * Désaffecter un acte (supprimer le lien avec le remboursement)
     * DELETE http://localhost:8080/api/actes/affectation/acte/{idActe}
     */
    @DeleteMapping("/acte/{idActe}")
    public ResponseEntity<Map<String, Object>> desaffecterActe(@PathVariable Long idActe) {
        try {
            Map<String, Object> resultat = affectationService.desaffecterActe(idActe);

            if (resultat.containsKey("erreur")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultat);
            }

            return ResponseEntity.ok(resultat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erreur", "Erreur lors de la désaffectation: " + e.getMessage()));
        }
    }
}
