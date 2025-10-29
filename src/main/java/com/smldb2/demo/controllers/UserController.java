package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.UnifiedLoginResponse;
import com.smldb2.demo.DTO.UserStatsDTO;
import com.smldb2.demo.DTO.UserDetailedStatsDTO;
import com.smldb2.demo.Entity.Famille;
import com.smldb2.demo.Entity.TypePrestataire;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.repositories.FamilleRepository;
import com.smldb2.demo.services.AuthService;
import com.smldb2.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "**")
public class UserController {
    @Autowired
    private FamilleRepository familleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    // ========== ENDPOINT LOGIN ==========

    @PostMapping("/login")
    public ResponseEntity<UnifiedLoginResponse> login(@RequestBody User loginRequest) {
        UnifiedLoginResponse response = authService.login(
                loginRequest.getPersoId(),
                loginRequest.getPersoPassed()
        );

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    // ========== ENDPOINTS UTILISATEURS ==========

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<User>> getUsersByEmail(@PathVariable String email) {
        List<User> users = userService.getUsersByEmail(email);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/sexe/{sexe}")
    public ResponseEntity<List<User>> getUsersBySexe(@PathVariable String sexe) {
        List<User> users = userService.getUsersBySexe(sexe);
        return ResponseEntity.ok(users);
    }

    // ========== ENDPOINT POUR RÉCUPÉRER LES ENTREPRISES ==========

    @GetMapping("/companies")
    public ResponseEntity<List<String>> getAllCompanies() {
        try {
            List<String> companies = userService.getAllCompanyCodes();
            return ResponseEntity.ok(companies);
        } catch (Exception e) {
            System.err.println("❌ Erreur dans getAllCompanies: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(List.of());
        }
    }

    // ========== STATISTIQUES GLOBALES ==========

    @GetMapping("/stats/global")
    public ResponseEntity<UserStatsDTO> getGlobalStats() {
        try {
            UserStatsDTO stats = userService.getGlobalStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("❌ Erreur dans getGlobalStats: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new UserStatsDTO(0L, 0L));
        }
    }

    @GetMapping("/stats/detailed")
    public ResponseEntity<UserDetailedStatsDTO> getDetailedStats() {
        try {
            UserDetailedStatsDTO stats = userService.getDetailedStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("❌ Erreur dans getDetailedStats: " + e.getMessage());
            e.printStackTrace();
            UserDetailedStatsDTO emptyStats = new UserDetailedStatsDTO();
            emptyStats.setRepartitionParSexe(new HashMap<>());
            emptyStats.setRepartitionParSituationFamiliale(new HashMap<>());
            return ResponseEntity.ok(emptyStats);
        }
    }

    @GetMapping("/stats/evolution")
    public ResponseEntity<Map<String, Long>> getEvolutionStats() {
        Map<String, Long> evolution = userService.getMonthlyEvolution();
        return ResponseEntity.ok(evolution);
    }

    // ========== STATISTIQUES PAR ENTREPRISE ==========

    @GetMapping("/stats/global/company/{codeEntreprise}")
    public ResponseEntity<UserStatsDTO> getGlobalStatsByCompany(@PathVariable String codeEntreprise) {
        try {
            UserStatsDTO stats = userService.getGlobalStatsByCompany(codeEntreprise);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("❌ Erreur dans getGlobalStatsByCompany: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new UserStatsDTO(0L, 0L));
        }
    }

    @GetMapping("/stats/detailed/company/{codeEntreprise}")
    public ResponseEntity<UserDetailedStatsDTO> getDetailedStatsByCompany(@PathVariable String codeEntreprise) {
        try {
            UserDetailedStatsDTO stats = userService.getDetailedStatsByCompany(codeEntreprise);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("❌ Erreur dans getDetailedStatsByCompany: " + e.getMessage());
            e.printStackTrace();
            UserDetailedStatsDTO emptyStats = new UserDetailedStatsDTO();
            emptyStats.setRepartitionParSexe(new HashMap<>());
            emptyStats.setRepartitionParSituationFamiliale(new HashMap<>());
            return ResponseEntity.ok(emptyStats);
        }
    }

    @GetMapping("/stats/evolution/company/{codeEntreprise}")
    public ResponseEntity<Map<String, Long>> getEvolutionStatsByCompany(@PathVariable String codeEntreprise) {
        try {
            Map<String, Long> evolution = userService.getMonthlyEvolutionByCompany(codeEntreprise);
            return ResponseEntity.ok(evolution);
        } catch (Exception e) {
            System.err.println("❌ Erreur dans getEvolutionStatsByCompany: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok(new HashMap<>());
        }
    }

    // ========== ENDPOINTS RIB ==========

    /**
     * Récupérer le RIB d'un utilisateur
     * GET /api/users/{persoId}/rib
     */
    @GetMapping("/{persoId}/rib")
    public ResponseEntity<Map<String, String>> getRibByPersoId(@PathVariable String persoId) {
        String rib = userService.getRibByPersoId(persoId);
        if (rib != null) {
            Map<String, String> response = new HashMap<>();
            response.put("persoId", persoId);
            response.put("rib", rib);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Modifier le RIB d'un utilisateur
     * PUT /api/users/{persoId}/rib
     * Body: { "rib": "12345678901234567890" }
     */
    @PutMapping("/{persoId}/rib")
    public ResponseEntity<Map<String, Object>> updateRib(
            @PathVariable String persoId,
            @RequestBody Map<String, String> request) {

        String newRib = request.get("rib");

        if (newRib == null || newRib.trim().isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Le RIB ne peut pas être vide");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Validation du format du RIB (20 chiffres)
        String cleanRib = newRib.replaceAll("\\s+", "");
        if (!cleanRib.matches("\\d{20}")) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Le RIB doit contenir exactement 20 chiffres");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        boolean success = userService.updateRib(persoId, cleanRib);

        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "RIB mis à jour avec succès");
            response.put("persoId", persoId);
            response.put("rib", cleanRib);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Impossible de mettre à jour le RIB");
            return ResponseEntity.status(500).body(response);
        }
    }

    // ========== ENDPOINTS CONTACT ==========

    /**
     * Récupérer le contact d'un utilisateur
     * GET /api/users/{persoId}/contact
     */
    @GetMapping("/{persoId}/contact")
    public ResponseEntity<Map<String, String>> getContactByPersoId(@PathVariable String persoId) {
        String contact = userService.getContactByPersoId(persoId);
        if (contact != null) {
            Map<String, String> response = new HashMap<>();
            response.put("persoId", persoId);
            response.put("contact", contact);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Modifier le contact d'un utilisateur
     * PUT /api/users/{persoId}/contact
     * Body: { "contact": "+216 12 345 678" }
     */
    @PutMapping("/{persoId}/contact")
    public ResponseEntity<Map<String, Object>> updateContact(
            @PathVariable String persoId,
            @RequestBody Map<String, String> request) {

        String newContact = request.get("contact");

        if (newContact == null || newContact.trim().isEmpty()) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Le contact ne peut pas être vide");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        // Validation du format du contact (numéro de téléphone)
        String cleanContact = newContact.replaceAll("\\s+", "");
        if (!cleanContact.matches("^\\+?\\d{8,15}$")) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Format de numéro de téléphone invalide");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        boolean success = userService.updateContact(persoId, newContact);

        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "Contact mis à jour avec succès");
            response.put("persoId", persoId);
            response.put("contact", newContact);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Impossible de mettre à jour le contact");
            return ResponseEntity.status(500).body(response);
        }
    }

    // ========== TEST ENDPOINT ==========

    @GetMapping("/stats/test")
    public ResponseEntity<Map<String, Object>> testEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "User stats controller is working");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }


// ========== ENDPOINTS POUR LES PRESTATAIRES ==========

    /**
     * Récupère le nombre total de prestataires (toutes entreprises)
     */
    @GetMapping("/stats/prestataires/total")
    public ResponseEntity<Long> getTotalPrestataires() {
        try {
            long total = familleRepository.count();
            return ResponseEntity.ok(total);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0L);
        }
    }

    /**
     * Récupère le nombre total de prestataires pour une entreprise
     */
    @GetMapping("/stats/prestataires/total/company/{codeEntreprise}")
    public ResponseEntity<Long> getTotalPrestatairesByCompany(
            @PathVariable String codeEntreprise) {
        try {
            // Récupérer tous les users de cette entreprise
            List<User> users = userService.getUsersByCompany(codeEntreprise);

            // Extraire les persoId
            List<String> persoIds = users.stream()
                    .map(User::getPersoId)
                    .collect(Collectors.toList());

            // Compter les prestataires liés à ces persoId
            long total = familleRepository.countByPersoIdIn(persoIds);

            return ResponseEntity.ok(total);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0L);
        }
    }

    /**
     * Récupère les statistiques détaillées des prestataires (toutes entreprises)
     */
    @GetMapping("/stats/prestataires/detailed")
    public ResponseEntity<Map<String, Object>> getDetailedPrestataireStats() {
        try {
            List<Famille> allPrestataires = familleRepository.findAll();

            long conjoints = allPrestataires.stream()
                    .filter(f -> TypePrestataire.CONJOINT.equals(f.getTypPrestataire()))
                    .count();

            long enfants = allPrestataires.stream()
                    .filter(f -> TypePrestataire.ENFANT.equals(f.getTypPrestataire()))
                    .count();

            Map<String, Object> result = new HashMap<>();
            result.put("total", allPrestataires.size());
            result.put("conjoints", conjoints);
            result.put("enfants", enfants);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<>());
        }
    }

    /**
     * Récupère les statistiques détaillées des prestataires par entreprise
     */
    @GetMapping("/stats/prestataires/detailed/company/{codeEntreprise}")
    public ResponseEntity<Map<String, Object>> getDetailedPrestataireStatsByCompany(
            @PathVariable String codeEntreprise) {
        try {
            List<User> users = userService.getUsersByCompany(codeEntreprise);
            List<String> persoIds = users.stream()
                    .map(User::getPersoId)
                    .collect(Collectors.toList());

            List<Famille> prestataires = familleRepository.findByPersoIdIn(persoIds);

            long conjoints = prestataires.stream()
                    .filter(f -> TypePrestataire.CONJOINT.equals(f.getTypPrestataire()))
                    .count();

            long enfants = prestataires.stream()
                    .filter(f -> TypePrestataire.ENFANT.equals(f.getTypPrestataire()))
                    .count();

            Map<String, Object> result = new HashMap<>();
            result.put("total", prestataires.size());
            result.put("conjoints", conjoints);
            result.put("enfants", enfants);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<>());
        }
    }


}