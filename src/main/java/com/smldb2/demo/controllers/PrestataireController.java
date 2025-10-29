package com.smldb2.demo.controllers;

import com.smldb2.demo.Entity.Prestataire;
import com.smldb2.demo.services.PrestataireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prestataires")
@CrossOrigin(origins = "**")
public class PrestataireController {

    @Autowired
    private PrestataireService prestataireService;

    // Créer un nouveau prestataire
    @PostMapping("/create")
    public ResponseEntity<Prestataire> creerPrestataire(@RequestBody Prestataire prestataire) {
        Prestataire created = prestataireService.creerPrestataire(prestataire);
        return ResponseEntity.ok(created);
    }

    // Récupérer tous les prestataires
    @GetMapping("/all")
    public ResponseEntity<List<Prestataire>> getAllPrestataires() {
        List<Prestataire> prestataires = prestataireService.getAllPrestataires();
        return ResponseEntity.ok(prestataires);
    }

    // Récupérer un prestataire par ID
    @GetMapping("/{persoId}")
    public ResponseEntity<Prestataire> getPrestataireById(@PathVariable String persoId) {
        Optional<Prestataire> prestataire = prestataireService.getPrestataireById(persoId);
        return prestataire.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Récupérer les prestataires par rôle (DENTISTE ou OPTICIEN)
    @GetMapping("/role/{role}")
    public ResponseEntity<List<Prestataire>> getPrestatairesByRole(@PathVariable String role) {
        List<Prestataire> prestataires = prestataireService.getPrestatairesByRole(role);
        return ResponseEntity.ok(prestataires);
    }

    // Mettre à jour un prestataire
    @PutMapping("/update/{persoId}")
    public ResponseEntity<Prestataire> updatePrestataire(
            @PathVariable String persoId,
            @RequestBody Prestataire prestataire) {
        Prestataire updated = prestataireService.updatePrestataire(persoId, prestataire);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // Supprimer un prestataire
    @DeleteMapping("/delete/{persoId}")
    public ResponseEntity<String> deletePrestataire(@PathVariable String persoId) {
        boolean deleted = prestataireService.deletePrestataire(persoId);
        if (deleted) {
            return ResponseEntity.ok("Prestataire supprimé avec succès");
        }
        return ResponseEntity.notFound().build();
    }
}