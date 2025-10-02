package com.smldb2.demo.controllers;
import com.smldb2.demo.Entity.Bssaisis;
import com.smldb2.demo.services.BssaisislService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bssaisis")
@CrossOrigin(origins = "**")
public class BssaisislController {
    @Autowired
    private BssaisislService bssaisislService;

    @GetMapping
    public ResponseEntity<List<Bssaisis>> getAllBssaisis() {
        List<Bssaisis> bssaisis = bssaisislService.getAllBssaisis();
        return ResponseEntity.ok(bssaisis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bssaisis> getBssaisislById(@PathVariable String id) {
        return bssaisislService.getBssaisislById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<List<Bssaisis>> getBssaisislByMatricule(@PathVariable String matricule) {
        List<Bssaisis> bssaisis = bssaisislService.getBssaisislByMatricule(matricule);
        return ResponseEntity.ok(bssaisis);
    }

    @GetMapping("/site/{codeSite}")
    public ResponseEntity<List<Bssaisis>> getBssaisislByCodeSite(@PathVariable String codeSite) {
        List<Bssaisis> bssaisis = bssaisislService.getBssaisislByCodeSite(codeSite);
        return ResponseEntity.ok(bssaisis);
    }
}
