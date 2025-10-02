package com.smldb2.demo.services;
import com.smldb2.demo.Entity.Famille;
import com.smldb2.demo.Entity.TypePrestataire;
import com.smldb2.demo.repositories.FamilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FamilleService {
    @Autowired
    private FamilleRepository familleRepository;

    public List<Famille> getAllFamilles() {
        return familleRepository.findAll();
    }

    public Optional<Famille> getFamilleById(String id) {
        return familleRepository.findById(id);
    }

    public List<Famille> getFamillesByPersoId(String persoId) {
        return familleRepository.findByPersoId(persoId);
    }

    public List<Famille> getFamillesByType(TypePrestataire type) {
        return familleRepository.findByTypPrestataire(type);
    }
}

