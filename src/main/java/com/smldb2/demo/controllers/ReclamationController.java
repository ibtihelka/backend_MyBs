package com.smldb2.demo.controllers;
import com.smldb2.demo.Entity.Reclamation;
import com.smldb2.demo.services.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reclamations")
@CrossOrigin(origins = "**")
public class ReclamationController {
    @Autowired
    private ReclamationService reclamationService;

    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        List<Reclamation> reclamations = reclamationService.getAllReclamations();
        return ResponseEntity.ok(reclamations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Integer id) {
        return reclamationService.getReclamationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{persoId}")
    public ResponseEntity<List<Reclamation>> getReclamationsByPersoId(@PathVariable String persoId) {
        List<Reclamation> reclamations = reclamationService.getReclamationsByPersoId(persoId);
        return ResponseEntity.ok(reclamations);
    }

    @GetMapping("/exported/{exported}")
    public ResponseEntity<List<Reclamation>> getReclamationsByExported(@PathVariable String exported) {
        List<Reclamation> reclamations = reclamationService.getReclamationsByExported(exported);
        return ResponseEntity.ok(reclamations);
    }

    @GetMapping("/ref/{refBsPhys}")
    public ResponseEntity<List<Reclamation>> getReclamationsByRefBsPhys(@PathVariable String refBsPhys) {
        List<Reclamation> reclamations = reclamationService.getReclamationsByRefBsPhys(refBsPhys);
        return ResponseEntity.ok(reclamations);
    }
}
