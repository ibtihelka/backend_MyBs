package com.smldb2.demo.services;
import com.smldb2.demo.Entity.Reclamation;
import com.smldb2.demo.repositories.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepository;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public Optional<Reclamation> getReclamationById(Integer id) {
        return reclamationRepository.findById(id);
    }

    public List<Reclamation> getReclamationsByPersoId(String persoId) {
        return reclamationRepository.findByPersoId(persoId);
    }

    public List<Reclamation> getReclamationsByExported(String exported) {
        return reclamationRepository.findByExported(exported);
    }

    public List<Reclamation> getReclamationsByRefBsPhys(String refBsPhys) {
        return reclamationRepository.findByRefBsPhys(refBsPhys);
    }
}
