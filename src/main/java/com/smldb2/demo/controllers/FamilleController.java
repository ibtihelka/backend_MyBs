package com.smldb2.demo.controllers;

import com.smldb2.demo.DTO.FamilleDTO;
import com.smldb2.demo.Entity.Famille;
import com.smldb2.demo.Entity.TypePrestataire;
import com.smldb2.demo.repositories.FamilleRepository;
import com.smldb2.demo.services.FamilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;




import java.util.List;

@RestController
@RequestMapping("/api/familles")
@CrossOrigin(origins = "**")
public class FamilleController {

    private final FamilleService familleService;
    private final FamilleRepository familleRepository;



    @GetMapping("/debug-count")
    public String debugCount() {
        long count = familleRepository.count();
        return "Familles trouvées = " + count;
    }


    // ✅ Injection par constructeur (meilleure pratique)
    @Autowired
    public FamilleController(FamilleService familleService, FamilleRepository familleRepository) {
        this.familleService = familleService;
        this.familleRepository = familleRepository;
    }

    @GetMapping
    public List<Famille> getAllFamilles() {
        return familleRepository.findAll(); // pas de pageable
    }



    @GetMapping("/count")
    public long countFamilles() {
        return familleRepository.count(); // ✅ instance
    }

    @GetMapping("/{id}")
    public ResponseEntity<Famille> getFamilleById(@PathVariable String id) {
        return familleService.getFamilleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{persoId}")
    public ResponseEntity<List<Famille>> getFamillesByPersoId(@PathVariable String persoId) {
        List<Famille> familles = familleService.getFamillesByPersoId(persoId);
        return ResponseEntity.ok(familles);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Famille>> getFamillesByType(@PathVariable TypePrestataire type) {
        List<Famille> familles = familleService.getFamillesByType(type);
        return ResponseEntity.ok(familles);
    }


    @GetMapping("/debug-full")
    public ResponseEntity<Map<String, Object>> debugFull() {
        List<Famille> allFamilles = familleRepository.findAll();
        Map<String, Object> debug = new HashMap<>();
        debug.put("totalFromRepo", allFamilles.size());
        debug.put("sampleData", allFamilles.stream().limit(5).collect(Collectors.toList()));
        return ResponseEntity.ok(debug);
    }


    @GetMapping("/with-pagination")
    public ResponseEntity<Map<String, Object>> getAllFamillesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Famille> famillePage = familleRepository.findAll(pageable);

        // Convertir en DTO
        List<FamilleDTO> familleDTOs = famillePage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("content", familleDTOs);
        response.put("totalElements", famillePage.getTotalElements());
        response.put("totalPages", famillePage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    private FamilleDTO convertToDTO(Famille famille) {
        FamilleDTO dto = new FamilleDTO();
        dto.setPrenomPrestataire(famille.getPrenomPrestataire());
        dto.setTypPrestataire(famille.getTypPrestataire());
        dto.setPersoId(famille.getPersoId());
        dto.setDatNais(famille.getDatNais());
        dto.setSexe(famille.getSexe());
        return dto;
    }

}
