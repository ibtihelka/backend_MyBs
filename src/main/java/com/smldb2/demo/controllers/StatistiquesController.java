package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.StatistiquesAdherantDTO;
import com.smldb2.demo.services.StatistiquesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
@CrossOrigin(origins = "**")
public class StatistiquesController {

    @Autowired
    private StatistiquesService statistiquesService;

    /**
     * Récupère toutes les statistiques pour un adhérant
     * GET /api/statistiques/adherant/{persoId}?annee=2020
     */
    @GetMapping("/adherant/{persoId}")
    public ResponseEntity<StatistiquesAdherantDTO> getStatistiquesAdherant(
            @PathVariable String persoId,
            @RequestParam(required = false) Integer annee) {

        try {
            StatistiquesAdherantDTO stats = statistiquesService.getStatistiquesAdherant(persoId, annee);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des statistiques: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Récupère les années disponibles pour un adhérant
     * GET /api/statistiques/adherant/{persoId}/annees
     */
    @GetMapping("/adherant/{persoId}/annees")
    public ResponseEntity<List<Integer>> getAnneesDisponibles(@PathVariable String persoId) {
        try {
            List<Integer> annees = statistiquesService.getAnneesDisponibles(persoId);
            return ResponseEntity.ok(annees);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des années: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }



    // 1️⃣ Nombre total de remboursements pour un perso_id
    @GetMapping("/total/{persoId}")
    public long getTotalRemboursements(@PathVariable String persoId) {
        return statistiquesService.getTotalRemboursements(persoId);
    }

    // 2️⃣ Nombre total dans une année
    @GetMapping("/total/{persoId}/{annee}")
    public long getTotalRemboursementsByYear(
            @PathVariable String persoId,
            @PathVariable Integer annee
    ) {
        return statistiquesService.getTotalRemboursementsByYear(persoId, annee);
    }

    // 3️⃣ Conjoint = rang 90 / 98 / 99
    @GetMapping("/conjoint/{persoId}/{annee}")
    public long getTotalConjointByYear(
            @PathVariable String persoId,
            @PathVariable Integer annee
    ) {
        return statistiquesService.getTotalConjointByYear(persoId, annee);
    }

    // 4️⃣ Adhérent = rang 0 / 00
    @GetMapping("/adherent/{persoId}/{annee}")
    public long getTotalAdherentByYear(
            @PathVariable String persoId,
            @PathVariable Integer annee
    ) {
        return statistiquesService.getTotalAdherentByYear(persoId, annee);
    }

    // 5️⃣ Enfant X = rang 01 / 1 / 02 / 2 ... 07 / 7
    @GetMapping("/enfant/{persoId}/{annee}/{num}")
    public long getTotalEnfantByYear(
            @PathVariable String persoId,
            @PathVariable Integer annee,
            @PathVariable Integer num
    ) {
        return statistiquesService.getTotalEnfantByYear(persoId, annee, num);
    }


    // 6️⃣ Statistiques réclamations
    @GetMapping("/reclamations/{persoId}/{annee}")
    public ResponseEntity<Map<String, Object>> getStatReclamations(
            @PathVariable String persoId,
            @PathVariable Integer annee
    ) {
        return ResponseEntity.ok(statistiquesService.getStatistiquesReclamations(persoId, annee));
    }

    // 7️⃣ Statistiques contre-visites
    @GetMapping("/contrevisites/{persoId}/{annee}")
    public ResponseEntity<Long> getStatContreVisites(
            @PathVariable String persoId,
            @PathVariable Integer annee
    ) {
        return ResponseEntity.ok(statistiquesService.getStatistiquesContreVisites(persoId, annee));
    }

    // 8️⃣ Évolution remboursements par mois
    @GetMapping("/evolution-mois/{persoId}/{annee}")
    public ResponseEntity<Map<String, Long>> getEvolutionParMois(
            @PathVariable String persoId,
            @PathVariable Integer annee
    ) {
        return ResponseEntity.ok(statistiquesService.getEvolutionRemboursementsParMois(persoId, annee));
    }

    // 9️⃣ Statistiques par année
    @GetMapping("/par-annee/{persoId}")
    public ResponseEntity<Map<Integer, Long>> getStatParAnnee(
            @PathVariable String persoId
    ) {
        return ResponseEntity.ok(statistiquesService.getStatistiquesParAnnee(persoId));
    }
}


