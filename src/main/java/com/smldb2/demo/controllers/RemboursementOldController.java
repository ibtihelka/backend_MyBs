package com.smldb2.demo.controllers;

import com.smldb2.demo.Entity.RemboursementOld;
import com.smldb2.demo.services.RemboursementOldService;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/remboursements-old")
@CrossOrigin(origins = "**")
public class RemboursementOldController {
    @Autowired
    private RemboursementOldService remboursementOldService;

    @GetMapping
    public ResponseEntity<List<RemboursementOld>> getAllRemboursementOlds() {
        List<RemboursementOld> remboursementOlds = remboursementOldService.getAllRemboursementOlds();
        return ResponseEntity.ok(remboursementOlds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RemboursementOld> getRemboursementOldById(@PathVariable String id) {
        return remboursementOldService.getRemboursementOldById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{persoId}")
    public ResponseEntity<List<RemboursementOld>> getRemboursementOldsByPersoId(@PathVariable String persoId) {
        List<RemboursementOld> remboursementOlds = remboursementOldService.getRemboursementOldsByPersoId(persoId);
        return ResponseEntity.ok(remboursementOlds);
    }
}
