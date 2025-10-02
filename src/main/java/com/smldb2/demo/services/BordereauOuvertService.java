package com.smldb2.demo.services;

import com.smldb2.demo.Entity.BordereauOuvert;
import com.smldb2.demo.repositories.BordereauOuvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BordereauOuvertService {
    @Autowired
    private BordereauOuvertRepository bordereauOuvertRepository;

    public List<BordereauOuvert> getAllBordereauOuverts() {
        return bordereauOuvertRepository.findAll();
    }

    public Optional<BordereauOuvert> getBordereauOuvertById(String id) {
        return bordereauOuvertRepository.findById(id);
    }

    public List<BordereauOuvert> getBordereauOuvertsByCodeEntite(String codeEntite) {
        return bordereauOuvertRepository.findByCodeEntite(codeEntite);
    }
}
