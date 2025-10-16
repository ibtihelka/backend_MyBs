package com.smldb2.demo.repositories;


import com.smldb2.demo.Entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

    // Trouver toutes les réclamations d'un utilisateur
    List<Reclamation> findByPersoId(String persoId);

    // Trouver les réclamations par refBsPhys
    List<Reclamation> findByRefBsPhys(String refBsPhys);

    // Vérifier si une réclamation existe déjà pour un remboursement
    boolean existsByRefBsPhys(String refBsPhys);

    // Trouver les réclamations d'un utilisateur par refBsPhys
    List<Reclamation> findByPersoIdAndRefBsPhys(String persoId, String refBsPhys);
}