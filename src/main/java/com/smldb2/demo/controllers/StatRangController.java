package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.StatMfDTO;
import com.smldb2.demo.DTO.StatRangDTO;
import com.smldb2.demo.DTO.StatistiqueGeneraleDTO;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.services.StatRangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/statistiques/rang")
@CrossOrigin(origins = "**")
public class StatRangController {

    @Autowired
    private StatRangService statRangService;

    /**
     * GET /api/statistiques/rang/societes
     * Récupère la liste des codes sociétés disponibles
     */
    @GetMapping("/societes")
    public ResponseEntity<List<String>> getSocietes() {
        System.out.println("🔵 Appel GET /api/statistiques/rang/societes");
        List<String> societes = statRangService.getAllSocietes();
        System.out.println("✅ Retour: " + societes.size() + " sociétés");
        return ResponseEntity.ok(societes);
    }

    /**
     * GET /api/statistiques/rang
     * Récupère les statistiques par rang pour une société et une période
     */
    @GetMapping
    public ResponseEntity<List<StatRangDTO>> getStatistiquesParRang(
            @RequestParam String codeSociete,
            @RequestParam String dateDebut,
            @RequestParam String dateFin) {

        System.out.println("========================================");
        System.out.println("🔵 Appel GET /api/statistiques/rang");
        System.out.println("   Société: " + codeSociete);
        System.out.println("   Date début: " + dateDebut);
        System.out.println("   Date fin: " + dateFin);
        System.out.println("========================================");

        try {
            List<StatRangDTO> statistics = statRangService.getStatistiquesParRang(
                    codeSociete, dateDebut, dateFin);

            System.out.println("✅ SUCCÈS: " + statistics.size() + " rangs retournés");
            return ResponseEntity.ok(statistics);

        } catch (ParseException e) {
            System.err.println("❌ ERREUR de parsing des dates: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("❌ ERREUR lors de la génération des statistiques:");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * GET /api/statistiques/rang/{rang}/details
     * Récupère les détails des remboursements pour un rang spécifique
     */
    @GetMapping("/{rang}/details")
    public ResponseEntity<List<Remboursement>> getRemboursementsByRang(
            @PathVariable String rang,
            @RequestParam String codeSociete,
            @RequestParam String dateDebut,
            @RequestParam String dateFin) {

        System.out.println("🔵 Appel GET /api/statistiques/rang/" + rang + "/details");

        try {
            List<Remboursement> remboursements = statRangService.getRemboursementsByRang(
                    rang, codeSociete, dateDebut, dateFin);

            System.out.println("✅ Retour: " + remboursements.size() + " remboursements");
            return ResponseEntity.ok(remboursements);

        } catch (ParseException e) {
            System.err.println("❌ ERREUR de parsing des dates: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("❌ ERREUR:");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    // StatRangController.java - Ajoutez cet endpoint
    /**
     * GET /api/statistiques/rang/generales
     * Récupère les statistiques générales pour une société et une période
     */
    @GetMapping("/generales")
    public ResponseEntity<StatistiqueGeneraleDTO> getStatistiquesGenerales(
            @RequestParam String codeSociete,
            @RequestParam String dateDebut,
            @RequestParam String dateFin) {

        System.out.println("🔵 Appel GET /api/statistiques/rang/generales");
        System.out.println("   Société: " + codeSociete);
        System.out.println("   Période: " + dateDebut + " - " + dateFin);

        try {
            StatistiqueGeneraleDTO stats = statRangService.getStatistiquesGenerales(
                    codeSociete, dateDebut, dateFin);

            System.out.println("✅ Stats générales: " + stats.getNombreTotalRemboursements() + " remboursements");
            return ResponseEntity.ok(stats);

        } catch (ParseException e) {
            System.err.println("❌ ERREUR de parsing des dates: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("❌ ERREUR:");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/mf")
    public ResponseEntity<List<StatMfDTO>> getStatistiquesParMf(
            @RequestParam String codeSociete,
            @RequestParam String dateDebut,
            @RequestParam String dateFin) {

        try {
            List<StatMfDTO> stats = statRangService.getStatistiquesParMf(codeSociete, dateDebut, dateFin);
            return ResponseEntity.ok(stats);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}