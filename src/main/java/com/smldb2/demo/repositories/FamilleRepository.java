package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Famille;
import com.smldb2.demo.Entity.TypePrestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FamilleRepository extends JpaRepository<Famille, String> {
    List<Famille> findByPersoIdStartingWith(String prefix);
    

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


    /**
     * Trouver une famille par le prénom du prestataire
     */
    Optional<Famille> findByPrenomPrestataire(String prenomPrestataire);

    /**
     * Trouver toutes les familles d'un utilisateur
     */
    List<Famille> findByPersoId(String persoId);

    /**
     * Trouver les familles par type de prestataire
     */
    List<Famille> findByTypPrestataire(TypePrestataire typPrestataire);

    /**
     * Compter le nombre de familles par type
     */
    long countByTypPrestataire(TypePrestataire typPrestataire);

    /**
     * Trouver les familles par sexe
     */
    List<Famille> findBySexe(String sexe);

    /**
     * Compter le nombre total de familles
     */
    @Query("SELECT COUNT(f) FROM Famille f")
    long countAllFamilles();

    /**
     * Trouver les familles d'un utilisateur par type
     */
    @Query("SELECT f FROM Famille f WHERE f.persoId = :persoId AND f.typPrestataire = :type")
    List<Famille> findByPersoIdAndType(@Param("persoId") String persoId,
                                       @Param("type") TypePrestataire type);
}


