package com.smldb2.demo.services;
import com.smldb2.demo.Entity.Bssaisis;
import com.smldb2.demo.repositories.BssaisislRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BssaisislService {
    @Autowired
    private BssaisislRepository bssaisislRepository;

    public List<Bssaisis> getAllBssaisis() {
        return bssaisislRepository.findAll();
    }

    public Optional<Bssaisis> getBssaisislById(String id) {
        return bssaisislRepository.findById(id);
    }

    public List<Bssaisis> getBssaisislByMatricule(String matricule) {
        return bssaisislRepository.findByMatricule(matricule);
    }

    public List<Bssaisis> getBssaisislByCodeSite(String codeSite) {
        return bssaisislRepository.findByCodeSite(codeSite);
    }
}
