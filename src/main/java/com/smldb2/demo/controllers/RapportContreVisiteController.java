package com.smldb2.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smldb2.demo.DTO.RapportCreationRequest;
import com.smldb2.demo.Entity.Famille;
import com.smldb2.demo.Entity.RapportContreVisite;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.services.RapportContreVisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/rapports")
@CrossOrigin(origins = "**")
public class RapportContreVisiteController {

    @Autowired
    private RapportContreVisiteService rapportService;

    @Autowired
    private ObjectMapper objectMapper; // ✅ Injection de ObjectMapper

    // ✅ Consultation des rapports d'un prestataire
    @GetMapping("/prestataire/{prestataireId}")
    public ResponseEntity<List<Map<String, String>>> getRapportsAvecType(@PathVariable String prestataireId) {
        return ResponseEntity.ok(rapportService.getRapportsAvecType(prestataireId));
    }

    // ✅ Liste des adhérents pour sélection frontend
    @GetMapping("/adherents")
    public ResponseEntity<List<User>> getAllAdherents() {
        return ResponseEntity.ok(rapportService.getAllAdherents());
    }

    // ✅ Liste des familles d'un adhérent
    @GetMapping("/famille/{persoId}")
    public ResponseEntity<List<Famille>> getFamilleByUser(@PathVariable String persoId) {
        return ResponseEntity.ok(rapportService.getFamilleByUser(persoId));
    }

    // ✅ Bulletins de soins d'un adhérent par persoId
    @GetMapping("/remboursements/{persoId}")
    public ResponseEntity<List<Remboursement>> getRemboursementsByUser(@PathVariable String persoId) {
        return ResponseEntity.ok(rapportService.getRemboursementsByUser(persoId));
    }

    // ✅ NOUVEAU: Bulletins de soins par CIN (matricule)
    @GetMapping("/remboursements-by-cin/{cin}")
    public ResponseEntity<List<Remboursement>> getRemboursementsByCin(@PathVariable String cin) {
        User adherent = rapportService.getAdherentByMatricule(cin);
        if (adherent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rapportService.getRemboursementsByUser(adherent.getPersoId()));
    }

    // ✅ Récupérer un adhérent par matricule (CIN)
    @GetMapping("/adherent/{matricule}")
    public ResponseEntity<User> getAdherentByMatricule(@PathVariable String matricule) {
        User adherent = rapportService.getAdherentByMatricule(matricule);
        if (adherent != null) {
            return ResponseEntity.ok(adherent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ NOUVEAU: Récupérer les bénéficiaires d'un bulletin de soins
    @GetMapping("/beneficiaires/{refBsPhys}")
    public ResponseEntity<Map<String, Object>> getBeneficiaires(@PathVariable String refBsPhys) {
        try {
            Optional<Remboursement> rembOpt = rapportService.getRemboursementsByUser("")
                    .stream()
                    .filter(r -> r.getRefBsPhys().equals(refBsPhys))
                    .findFirst();

            if (!rembOpt.isPresent()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Remboursement introuvable");
                return ResponseEntity.badRequest().body(error);
            }

            Remboursement remboursement = rembOpt.get();
            String persoId = remboursement.getPersoId();

            Optional<User> userOpt = Optional.ofNullable(rapportService.getAllAdherents()
                    .stream()
                    .filter(u -> u.getPersoId().equals(persoId))
                    .findFirst()
                    .orElse(null));

            if (!userOpt.isPresent()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Adhérent introuvable");
                return ResponseEntity.badRequest().body(error);
            }

            User user = userOpt.get();
            List<Map<String, String>> beneficiaires = new ArrayList<>();

            // Ajouter l'adhérent principal
            Map<String, String> adherentMap = new HashMap<>();
            adherentMap.put("id", user.getPersoId());
            adherentMap.put("nom", user.getPersoName());
            adherentMap.put("type", "ADHERENT");
            beneficiaires.add(adherentMap);

            // Ajouter les membres de la famille
            List<Famille> familles = rapportService.getFamilleByUser(persoId);
            for (Famille famille : familles) {
                Map<String, String> familleMap = new HashMap<>();
                familleMap.put("id", famille.getPrenomPrestataire());
                familleMap.put("nom", famille.getPrenomPrestataire());
                familleMap.put("type", famille.getTypPrestataire().name());
                beneficiaires.add(familleMap);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("beneficiaires", beneficiaires);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erreur: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // ✅ Créer un rapport AVEC IMAGE (FormData)
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> creerRapport(
            @RequestParam("matriculeAdherent") String matriculeAdherent,
            @RequestParam("refBsPhys") String refBsPhys,
            @RequestParam("prestataireId") String prestataireId,
            @RequestPart("rapport") String rapportJson,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        System.out.println("✅ Requête reçue");
        System.out.println("📄 Matricule : " + matriculeAdherent);
        System.out.println("📄 RefBsPhys : " + refBsPhys);
        System.out.println("📄 PrestataireId : " + prestataireId);
        System.out.println("🖼️ Image : " + (image != null ? image.getOriginalFilename() : "Aucune"));

        try {
            // Désérialiser le JSON du rapport
            RapportContreVisite rapport = objectMapper.readValue(rapportJson, RapportContreVisite.class);
            System.out.println("📄 Type rapport : " + rapport.getTypeRapport());

            // Appel du service avec image
            Map<String, Object> response = rapportService.creerRapportParMatricule(
                    matriculeAdherent,
                    refBsPhys,
                    prestataireId,
                    image,
                    rapport
            );

            if ((boolean) response.get("success")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Erreur lors de la création du rapport: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Récupère le bénéficiaire d'un bulletin de soins par refBsPhys
     */
    @GetMapping("/beneficiaire/{refBsPhys}")
    public ResponseEntity<Map<String, Object>> getBeneficiaire(@PathVariable String refBsPhys) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, String> beneficiaire = rapportService.getBeneficiaireParRefBsPhys(refBsPhys);
            response.put("success", true);
            response.put("beneficiaire", beneficiaire);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/beneficiaire-id/{beneficiaireId}")
    public ResponseEntity<List<Map<String, Object>>> getRapportsByBeneficiaireId(@PathVariable String beneficiaireId) {
        List<Map<String, Object>> rapports = rapportService.getRapportsByBeneficiaireId(beneficiaireId);
        if (rapports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rapports);
    }

    @GetMapping("/prestataire/details/{prestataireId}")
    public ResponseEntity<List<Map<String, Object>>> getRapportsDetailsByPrestataire(@PathVariable String prestataireId) {
        List<Map<String, Object>> rapports = rapportService.getRapportsDetailsByPrestataire(prestataireId);
        if (rapports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rapports);
    }





}