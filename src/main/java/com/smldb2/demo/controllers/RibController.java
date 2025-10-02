package com.smldb2.demo.controllers;
import com.smldb2.demo.Entity.Rib;
import com.smldb2.demo.services.RibService;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/rib")
@CrossOrigin(origins = "**")
public class RibController {
    @Autowired
    private RibService ribService;

    @GetMapping
    public ResponseEntity<List<Rib>> getAllRibs() {
        List<Rib> ribs = ribService.getAllRibs();
        return ResponseEntity.ok(ribs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rib> getRibById(@PathVariable Integer id) {
        return ribService.getRibById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{persoId}")
    public ResponseEntity<List<Rib>> getRibsByPersoId(@PathVariable String persoId) {
        List<Rib> ribs = ribService.getRibsByPersoId(persoId);
        return ResponseEntity.ok(ribs);
    }

    @GetMapping("/exported/{exported}")
    public ResponseEntity<List<Rib>> getRibsByExported(@PathVariable String exported) {
        List<Rib> ribs = ribService.getRibsByExported(exported);
        return ResponseEntity.ok(ribs);
    }
}

