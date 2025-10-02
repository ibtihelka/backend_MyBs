package com.smldb2.demo.controllers;

import com.smldb2.demo.Entity.BordereauOuvert;
import com.smldb2.demo.services.BordereauOuvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bordereau-ouvert")
@CrossOrigin(origins = "**")
public class BordereauOuvertController {
    @Autowired
    private BordereauOuvertService bordereauOuvertService;

    @GetMapping
    public ResponseEntity<List<BordereauOuvert>> getAllBordereauOuverts() {
        List<BordereauOuvert> bordereauOuverts = bordereauOuvertService.getAllBordereauOuverts();
        return ResponseEntity.ok(bordereauOuverts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BordereauOuvert> getBordereauOuvertById(@PathVariable String id) {
        return bordereauOuvertService.getBordereauOuvertById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/entite/{codeEntite}")
    public ResponseEntity<List<BordereauOuvert>> getBordereauOuvertsByCodeEntite(@PathVariable String codeEntite) {
        List<BordereauOuvert> bordereauOuverts = bordereauOuvertService.getBordereauOuvertsByCodeEntite(codeEntite);
        return ResponseEntity.ok(bordereauOuverts);
    }
}
