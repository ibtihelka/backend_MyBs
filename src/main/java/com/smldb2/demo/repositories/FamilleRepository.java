package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Famille;
import com.smldb2.demo.Entity.TypePrestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface FamilleRepository extends JpaRepository<Famille, String> {
    
    List<Famille> findByPersoId(String persoId);

    List<Famille> findByTypPrestataire(TypePrestataire typPrestataire);

    // ✅ Nouvelles méthodes pour les statistiques des prestataires

    /**
     * Compte tous les prestataires
     */
    long count();

    /**
     * Compte les prestataires pour une liste de persoId (entreprise)
     */
    long countByPersoIdIn(List<String> persoIds);

    /**
     * Trouve tous les prestataires pour une liste de persoId
     */
    List<Famille> findByPersoIdIn(List<String> persoIds);

    /**
     * Compte les prestataires par type
     */
    long countByTypPrestataire(String typPrestataire);

    /**
     * Compte les prestataires par type pour une liste de persoId
     */
    long countByPersoIdInAndTypPrestataire(List<String> persoIds, String typPrestataire);
}
