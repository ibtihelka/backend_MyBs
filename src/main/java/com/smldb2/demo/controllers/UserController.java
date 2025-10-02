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

    // Endpoint de connexion unifié
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





    // Méthode helper pour calculer les nouveaux utilisateurs du mois
    private int calculateNewUsersThisMonth(List<User> users) {
        // Implémentation simple - à adapter selon votre logique métier
        // Par exemple, si vous avez un champ dateInscription dans User
        // Sinon, retourner 0 ou une autre logique
        return 0; // À modifier selon vos besoins
    }

    // Endpoints existants
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



    /**
     * Obtenir les statistiques globales des adhérents
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
            // Retourner des valeurs par défaut en cas d'erreur
            return ResponseEntity.ok(new UserStatsDTO(0L, 0L));
        }
    }

    /**
     * Obtenir les statistiques détaillées des adhérents
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
            // Retourner des stats vides en cas d'erreur
            UserDetailedStatsDTO emptyStats = new UserDetailedStatsDTO();
            emptyStats.setRepartitionParSexe(new HashMap<>());
            emptyStats.setRepartitionParSituationFamiliale(new HashMap<>());
            return ResponseEntity.ok(emptyStats);
        }
    }

    /**
     * Test endpoint pour vérifier que le controller fonctionne
     * GET /api/users/stats/test
     */
    @GetMapping("/stats/test")
    public ResponseEntity<Map<String, Object>> testEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "User stats controller is working");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }



    // Dans UserController.java
    @GetMapping("/stats/evolution")
    public ResponseEntity<Map<String, Long>> getEvolutionStats() {
        Map<String, Long> evolution = userService.getMonthlyEvolution();
        return ResponseEntity.ok(evolution);
    }
}
