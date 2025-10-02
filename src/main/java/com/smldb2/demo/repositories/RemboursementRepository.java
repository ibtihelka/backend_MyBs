package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, String> {
    List<Remboursement> findByPersoId(String persoId);
    List<Remboursement> findByStatBs(String statBs);
    List<Remboursement> findByRefBorderau(String refBorderau);
}
