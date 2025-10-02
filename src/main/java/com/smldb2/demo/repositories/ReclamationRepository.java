package com.smldb2.demo.repositories;


import com.smldb2.demo.Entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 10. ReclamationRepository
@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Integer> {
    List<Reclamation> findByPersoId(String persoId);
    List<Reclamation> findByExported(String exported);
    List<Reclamation> findByRefBsPhys(String refBsPhys);
}

