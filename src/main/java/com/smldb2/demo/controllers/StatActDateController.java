package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.ActeDetailDTO;
import com.smldb2.demo.DTO.ReportingResponseDTO;
import com.smldb2.demo.DTO.StatCodDoctCvDTO;
import com.smldb2.demo.DTO.StatMatDTO;
import com.smldb2.demo.Entity.Remboursement;
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
        System.out.println("🔵 Appel GET /api/statistiques/societes");
        List<String> societes = statActDateService.getAllSocietes();
        System.out.println("✅ Retour: " + societes.size() + " sociétés");
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

        System.out.println("========================================");
        System.out.println("🔵 Appel GET /api/statistiques/reporting");
        System.out.println("   Société: " + codeSociete);
        System.out.println("   Date début: " + dateDebut);
        System.out.println("   Date fin: " + dateFin);
        System.out.println("========================================");

        try {
            ReportingResponseDTO reporting = statActDateService
                    .getReportingBySocieteAndPeriode(codeSociete, dateDebut, dateFin);

            System.out.println("========================================");
            System.out.println("✅ REPORTING GÉNÉRÉ AVEC SUCCÈS");
            System.out.println("   Nombre d'adhérents: " + reporting.getNombreAdherents());
            System.out.println("   Nombre d'actes: " + (reporting.getActes() != null ? reporting.getActes().size() : 0));
            System.out.println("   Nombre de RIBs: " + (reporting.getRibs() != null ? reporting.getRibs().size() : 0));
            System.out.println("========================================");

            return ResponseEntity.ok(reporting);
        } catch (Exception e) {
            System.err.println("❌ ERREUR lors de la génération du reporting:");
            e.printStackTrace();
            throw e;
        }
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

        System.out.println("🔵 Appel GET /api/statistiques/adherents");

        List<String> matricules = statActDateService
                .getMatriculesBySocieteAndPeriode(codeSociete, dateDebut, dateFin);

        System.out.println("✅ Retour: " + matricules.size() + " matricules");
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

        System.out.println("🔵 Appel GET /api/statistiques/adherents/" + matricule + "/actes");

        List<ActeDetailDTO> actes = statActDateService
                .getActesByMatricule(matricule, codeSociete, dateDebut, dateFin);

        System.out.println("✅ Retour: " + actes.size() + " actes");
        return ResponseEntity.ok(actes);
    }


    // Ajoutez ces méthodes dans StatActDateController.java

    /**
     * GET /api/statistiques/mat
     * Récupère les statistiques par MAT (matricule prestataire)
     */
    @GetMapping("/mat")
    public ResponseEntity<List<StatMatDTO>> getStatistiquesParMat(
            @RequestParam String codeSociete,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        System.out.println("========================================");
        System.out.println("🔵 Appel GET /api/statistiques/mat");
        System.out.println("   Société: " + codeSociete);
        System.out.println("   Date début: " + dateDebut);
        System.out.println("   Date fin: " + dateFin);
        System.out.println("========================================");

        try {
            List<StatMatDTO> stats = statActDateService
                    .getStatistiquesParMat(codeSociete, dateDebut, dateFin);

            System.out.println("✅ Statistiques MAT générées: " + stats.size() + " entrées");
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("❌ ERREUR lors de la génération des stats MAT:");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * GET /api/statistiques/mat/{mat}/remboursements
     * Récupère les remboursements détaillés pour un MAT spécifique
     */
    @GetMapping("/mat/{mat}/remboursements")
    public ResponseEntity<List<Remboursement>> getRemboursementsByMat(
            @PathVariable String mat,
            @RequestParam String codeSociete,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        System.out.println("🔵 Appel GET /api/statistiques/mat/" + mat + "/remboursements");

        List<Remboursement> remboursements = statActDateService
                .getRemboursementsByMat(codeSociete, dateDebut, dateFin, mat);

        System.out.println("✅ Retour: " + remboursements.size() + " remboursements");
        return ResponseEntity.ok(remboursements);
    }


    // Ajoutez ces méthodes dans StatActDateController.java

    /**
     * GET /api/statistiques/cod-doct-cv
     * Récupère les statistiques par COD_DOCT_CV
     */
    @GetMapping("/cod-doct-cv")
    public ResponseEntity<List<StatCodDoctCvDTO>> getStatistiquesParCodDoctCv(
            @RequestParam String codeSociete,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        System.out.println("========================================");
        System.out.println("🔵 Appel GET /api/statistiques/cod-doct-cv");
        System.out.println("   Société: " + codeSociete);
        System.out.println("   Date début: " + dateDebut);
        System.out.println("   Date fin: " + dateFin);
        System.out.println("========================================");

        try {
            List<StatCodDoctCvDTO> stats = statActDateService
                    .getStatistiquesParCodDoctCv(codeSociete, dateDebut, dateFin);

            System.out.println("✅ Statistiques COD_DOCT_CV générées: " + stats.size() + " entrées");
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("❌ ERREUR lors de la génération des stats COD_DOCT_CV:");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * GET /api/statistiques/cod-doct-cv/{codDoctCv}/remboursements
     * Récupère les remboursements détaillés pour un COD_DOCT_CV spécifique
     */
    @GetMapping("/cod-doct-cv/{codDoctCv}/remboursements")
    public ResponseEntity<List<Remboursement>> getRemboursementsByCodDoctCv(
            @PathVariable String codDoctCv,
            @RequestParam String codeSociete,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {

        System.out.println("🔵 Appel GET /api/statistiques/cod-doct-cv/" + codDoctCv + "/remboursements");

        List<Remboursement> remboursements = statActDateService
                .getRemboursementsByCodDoctCv(codeSociete, dateDebut, dateFin, codDoctCv);

        System.out.println("✅ Retour: " + remboursements.size() + " remboursements");
        return ResponseEntity.ok(remboursements);
    }
}