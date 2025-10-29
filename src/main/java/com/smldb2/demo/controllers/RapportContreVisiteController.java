package com.smldb2.demo.controllers;

import com.smldb2.demo.Entity.RapportContreVisite;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.services.RapportContreVisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rapports")
@CrossOrigin(origins = "**")
public class RapportContreVisiteController {

    @Autowired
    private RapportContreVisiteService rapportService;

    // Créer un nouveau rapport avec validation du rôle et envoi d'image par email
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> creerRapport(
            @RequestPart("rapport") String rapportJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        Map<String, Object> response = rapportService.creerRapportAvecImage(rapportJson, image);

        boolean success = (boolean) response.get("success");
        if (success) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Récupérer les bénéficiaires pour un remboursement
    @GetMapping("/beneficiaires/{refBsPhys}")
    public ResponseEntity<Map<String, Object>> getBeneficiaires(@PathVariable String refBsPhys) {
        Map<String, Object> beneficiaires = rapportService.getBeneficiaires(refBsPhys);
        return ResponseEntity.ok(beneficiaires);
    }

    // Récupérer tous les remboursements
    @GetMapping("/remboursements")
    public ResponseEntity<List<Remboursement>> getAllRemboursements() {
        List<Remboursement> remboursements = rapportService.getAllRemboursements();
        return ResponseEntity.ok(remboursements);
    }

    // Récupérer les rapports d'un prestataire
    @GetMapping("/prestataire/{prestataireId}")
    public ResponseEntity<List<RapportContreVisite>> getRapportsParPrestataire(@PathVariable String prestataireId) {
        List<RapportContreVisite> rapports = rapportService.getRapportsParPrestataire(prestataireId);
        return ResponseEntity.ok(rapports);
    }
}