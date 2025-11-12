package com.smldb2.demo.controllers;

import com.smldb2.demo.Entity.UsersSociete;
import com.smldb2.demo.services.UsersSocieteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/societes")
@CrossOrigin(origins = "**")
public class UsersSocieteController {

    @Autowired
    private UsersSocieteService usersSocieteService;

    @GetMapping
    public ResponseEntity<List<UsersSociete>> getAllUsersSociete() {
        List<UsersSociete> societes = usersSocieteService.getAllUsersSociete();
        return ResponseEntity.ok(societes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersSociete> getUserSocieteById(@PathVariable String id) {
        return usersSocieteService.getUserSocieteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UsersSociete> getUserSocieteByName(@PathVariable String name) {
        return usersSocieteService.getUserSocieteByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
