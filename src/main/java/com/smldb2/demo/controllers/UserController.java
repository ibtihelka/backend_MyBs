package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.UnifiedLoginResponse;
import com.smldb2.demo.DTO.UserStatsDTO;
import com.smldb2.demo.DTO.UserDetailedStatsDTO;
import com.smldb2.demo.Entity.User;
import com.smldb2.demo.services.AuthService;
import com.smldb2.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "**")
public class UserController {

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

    /**
     * Récupérer la liste de tous les codes d'entreprise
     * GET /api/users/companies
     */
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

    // ========== STATISTIQUES GLOBALES (TOUTES ENTREPRISES) ==========

    /**
     * GET /api/users/stats/global
     */
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

    /**
     * GET /api/users/stats/detailed
     */
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

    /**
     * GET /api/users/stats/evolution
     */
    @GetMapping("/stats/evolution")
    public ResponseEntity<Map<String, Long>> getEvolutionStats() {
        Map<String, Long> evolution = userService.getMonthlyEvolution();
        return ResponseEntity.ok(evolution);
    }

    // ========== STATISTIQUES PAR ENTREPRISE ==========

    /**
     * Statistiques globales pour une entreprise spécifique
     * GET /api/users/stats/global/company/{codeEntreprise}
     * Exemple: GET /api/users/stats/global/company/SO000006
     */
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

    /**
     * Statistiques détaillées pour une entreprise spécifique
     * GET /api/users/stats/detailed/company/{codeEntreprise}
     * Exemple: GET /api/users/stats/detailed/company/SO000006
     */
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

    /**
     * Évolution mensuelle pour une entreprise spécifique
     * GET /api/users/stats/evolution/company/{codeEntreprise}
     * Exemple: GET /api/users/stats/evolution/company/SO000006
     */
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

    // ========== TEST ENDPOINT ==========

    @GetMapping("/stats/test")
    public ResponseEntity<Map<String, Object>> testEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "User stats controller is working");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }
}