package com.smldb2.demo.controllers;

import com.smldb2.demo.Entity.Suggestion;
import com.smldb2.demo.services.SuggestionService;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/suggestions")
@CrossOrigin(origins = "**")
public class SuggestionController {
    @Autowired
    private SuggestionService suggestionService;

    @GetMapping
    public ResponseEntity<List<Suggestion>> getAllSuggestions() {
        List<Suggestion> suggestions = suggestionService.getAllSuggestions();
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suggestion> getSuggestionById(@PathVariable Integer id) {
        return suggestionService.getSuggestionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{persoId}")
    public ResponseEntity<List<Suggestion>> getSuggestionsByPersoId(@PathVariable String persoId) {
        List<Suggestion> suggestions = suggestionService.getSuggestionsByPersoId(persoId);
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping("/exported/{exported}")
    public ResponseEntity<List<Suggestion>> getSuggestionsByExported(@PathVariable String exported) {
        List<Suggestion> suggestions = suggestionService.getSuggestionsByExported(exported);
        return ResponseEntity.ok(suggestions);
    }
}
