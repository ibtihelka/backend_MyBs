package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByPersoName(String persoName);
    List<User> findByEmailContaining(String email);
    List<User> findBySexe(String sexe);
    Optional<User> findByPersoIdAndPersoPassed(String persoId, String persoPassed);
    Optional<User> findByEmail(String email);
    Optional<User> findByCin(String cin);
    long countByDateCreationAfter(Date date);

    // ========== NOUVELLES MÉTHODES POUR FILTRER PAR ENTREPRISE ==========

    /**
     * Trouver tous les utilisateurs d'une entreprise
     * Utilise SUBSTRING pour extraire les 8 premiers caractères du persoId
     */
    @Query("SELECT u FROM User u WHERE SUBSTRING(u.persoId, 1, 8) = :codeEntreprise")
    List<User> findByCodeEntreprise(@Param("codeEntreprise") String codeEntreprise);

    /**
     * Compter le nombre total d'utilisateurs d'une entreprise
     */
    @Query("SELECT COUNT(u) FROM User u WHERE SUBSTRING(u.persoId, 1, 8) = :codeEntreprise")
    long countByCodeEntreprise(@Param("codeEntreprise") String codeEntreprise);

    /**
     * Compter les nouveaux utilisateurs d'une entreprise depuis une date
     */
    @Query("SELECT COUNT(u) FROM User u WHERE SUBSTRING(u.persoId, 1, 8) = :codeEntreprise " +
            "AND u.dateCreation > :startDate")
    long countByCodeEntrepriseAndDateCreationAfter(
            @Param("codeEntreprise") String codeEntreprise,
            @Param("startDate") Date startDate
    );

    /**
     * Récupérer tous les codes d'entreprise distincts
     */
    @Query("SELECT DISTINCT SUBSTRING(u.persoId, 1, 8) FROM User u ORDER BY SUBSTRING(u.persoId, 1, 8)")
    List<String> findAllDistinctCompanyCodes();
}