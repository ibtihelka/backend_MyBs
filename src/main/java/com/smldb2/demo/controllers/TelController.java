package com.smldb2.demo.controllers;

import com.smldb2.demo.Entity.Tel;
import com.smldb2.demo.services.TelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tels")
@CrossOrigin(origins = "**")
public class TelController {
    @Autowired
    private TelService telService;

    @GetMapping
    public ResponseEntity<List<Tel>> getAllTels() {
        List<Tel> tels = telService.getAllTels();
        return ResponseEntity.ok(tels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tel> getTelById(@PathVariable Integer id) {
        return telService.getTelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{persoId}")
    public ResponseEntity<List<Tel>> getTelsByPersoId(@PathVariable String persoId) {
        List<Tel> tels = telService.getTelsByPersoId(persoId);
        return ResponseEntity.ok(tels);
    }

    @GetMapping("/exported/{exported}")
    public ResponseEntity<List<Tel>> getTelsByExported(@PathVariable String exported) {
        List<Tel> tels = telService.getTelsByExported(exported);
        return ResponseEntity.ok(tels);
    }
}

