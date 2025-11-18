package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Rib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RibRepository extends JpaRepository<Rib, Integer> {
    List<Rib> findByPersoId(String persoId);
    List<Rib> findByExported(String exported);



    /**
     * Trouve les RIBs d'un utilisateur avec un statut exported spécifique
     * Trié par date de création décroissante (le plus récent en premier)
     */
    @Query("SELECT r FROM Rib r WHERE r.persoId = :persoId AND r.exported = :exported ORDER BY r.dateCreation DESC")
    List<Rib> findByPersoIdAndExported(@Param("persoId") String persoId, @Param("exported") String exported);

    /**
     * Trouve le RIB le plus récent en attente pour un utilisateur
     */
    @Query("SELECT r FROM Rib r WHERE r.persoId = :persoId AND r.exported = 'N' ORDER BY r.dateCreation DESC")
    Optional<Rib> findLatestPendingRibByPersoId(@Param("persoId") String persoId);
}

