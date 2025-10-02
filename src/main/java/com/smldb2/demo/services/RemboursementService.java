package com.smldb2.demo.services;
import com.smldb2.demo.Entity.Remboursement;
import com.smldb2.demo.repositories.RemboursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RemboursementService {
    @Autowired
    private RemboursementRepository remboursementRepository;

    public List<Remboursement> getAllRemboursements() {
        return remboursementRepository.findAll();
    }

    public Optional<Remboursement> getRemboursementById(String id) {
        return remboursementRepository.findById(id);
    }

    public List<Remboursement> getRemboursementsByPersoId(String persoId) {
        return remboursementRepository.findByPersoId(persoId);
    }

    public List<Remboursement> getRemboursementsByStatus(String status) {
        return remboursementRepository.findByStatBs(status);
    }

    public List<Remboursement> getRemboursementsByBordereau(String refBordereau) {
        return remboursementRepository.findByRefBorderau(refBordereau);
    }
}
