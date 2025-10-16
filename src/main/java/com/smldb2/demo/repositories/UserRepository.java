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

    // ========== MÉTHODES POUR FILTRER PAR ENTREPRISE ==========

    @Query("SELECT u FROM User u WHERE SUBSTRING(u.persoId, 1, 8) = :codeEntreprise")
    List<User> findByCodeEntreprise(@Param("codeEntreprise") String codeEntreprise);

    @Query("SELECT COUNT(u) FROM User u WHERE SUBSTRING(u.persoId, 1, 8) = :codeEntreprise")
    long countByCodeEntreprise(@Param("codeEntreprise") String codeEntreprise);

    @Query("SELECT COUNT(u) FROM User u WHERE SUBSTRING(u.persoId, 1, 8) = :codeEntreprise " +
            "AND u.dateCreation > :startDate")
    long countByCodeEntrepriseAndDateCreationAfter(
            @Param("codeEntreprise") String codeEntreprise,
            @Param("startDate") Date startDate
    );

    @Query("SELECT DISTINCT SUBSTRING(u.persoId, 1, 8) FROM User u ORDER BY SUBSTRING(u.persoId, 1, 8)")
    List<String> findAllDistinctCompanyCodes();

    // ========== MÉTHODES POUR RIB ET CONTACT ==========

    /**
     * Récupérer le RIB d'un utilisateur
     */
    @Query("SELECT u.rib FROM User u WHERE u.persoId = :persoId")
    String findRibByPersoId(@Param("persoId") String persoId);

    /**
     * Récupérer le contact d'un utilisateur
     */
    @Query("SELECT u.contact FROM User u WHERE u.persoId = :persoId")
    String findContactByPersoId(@Param("persoId") String persoId);

    // ========== MÉTHODES DE RECHERCHE ==========

    Optional<User> findByPersoId(String persoId);

    boolean existsByPersoId(String persoId);

    boolean existsByEmail(String email);
}