package com.smldb2.demo.repositories;


import com.smldb2.demo.Entity.RapportContreVisite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RapportContreVisiteRepository extends JpaRepository<RapportContreVisite, Long> {
    List<RapportContreVisite> findByPrestataireId(String prestataireId);
   List<RapportContreVisite> findByRefBsPhys(String refBsPhys);
    List<RapportContreVisite> findByBeneficiaireId(String beneficiaireId);

    // ✅ NOUVELLE MÉTHODE : Vérifier si un rapport existe pour un BS
    boolean existsByRefBsPhys(String refBsPhys);

    Optional<RapportContreVisite> findFirstByRefBsPhys(String refBsPhys);
}
