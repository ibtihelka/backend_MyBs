package com.smldb2.demo.services;

import com.smldb2.demo.Entity.RemboursementOld;
import com.smldb2.demo.repositories.RemboursementOldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RemboursementOldService {
    @Autowired
    private RemboursementOldRepository remboursementOldRepository;

    public List<RemboursementOld> getAllRemboursementOlds() {
        return remboursementOldRepository.findAll();
    }

    public Optional<RemboursementOld> getRemboursementOldById(String id) {
        return remboursementOldRepository.findById(id);
    }

    public List<RemboursementOld> getRemboursementOldsByPersoId(String persoId) {
        return remboursementOldRepository.findByPersoId(persoId);
    }
}
