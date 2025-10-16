package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, String> {
    List<Remboursement> findByPersoId(String persoId);
    List<Remboursement> findByStatBs(String statBs);
    List<Remboursement> findByRefBorderau(String refBorderau);


    // Trouver un remboursement par refBsPhys
    Optional<Remboursement> findByRefBsPhys(String refBsPhys);

    // Trouver les remboursements d'un utilisateur avec un statut spécifique
    List<Remboursement> findByPersoIdAndStatBs(String persoId, String statBs);

}
