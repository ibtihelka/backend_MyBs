package com.smldb2.demo.repositories;


import com.smldb2.demo.Entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    long countByRefBsPhys(String refBsPhys);


    // ✅ NOUVELLE MÉTHODE - Compter les réclamations par utilisateur
    long countByPersoId(String persoId);

    // Méthodes supplémentaires utiles
    @Query("SELECT COUNT(r) FROM Reclamation r WHERE r.persoId = :persoId AND r.responseRec IS NOT NULL")
    long countReclamationsRepondusByPersoId(@Param("persoId") String persoId);

    @Query("SELECT COUNT(r) FROM Reclamation r WHERE r.persoId = :persoId AND r.responseRec IS NULL")
    long countReclamationsEnAttenteByPersoId(@Param("persoId") String persoId);



    @Query("SELECT COUNT(r) FROM Reclamation r WHERE r.persoId = :persoId " +
            "AND YEAR(r.dateCreation) = :annee")
    long countByPersoIdAndYear(@Param("persoId") String persoId,
                               @Param("annee") Integer annee);


    // Compter les réclamations avec réponse par année
    @Query("SELECT COUNT(r) FROM Reclamation r WHERE r.persoId = :persoId " +
            "AND YEAR(r.dateCreation) = :annee " +
            "AND r.responseRec IS NOT NULL AND r.responseRec != ''")
    long countReclamationsAvecReponseByYear(@Param("persoId") String persoId,
                                            @Param("annee") Integer annee);

    // Compter les réclamations sans réponse par année
    @Query("SELECT COUNT(r) FROM Reclamation r WHERE r.persoId = :persoId " +
            "AND YEAR(r.dateCreation) = :annee " +
            "AND (r.responseRec IS NULL OR r.responseRec = '')")
    long countReclamationsSansReponseByYear(@Param("persoId") String persoId,
                                            @Param("annee") Integer annee);

    // Récupérer toutes les réclamations d'un adhérant (pour évolution annuelle)
    @Query("SELECT r FROM Reclamation r WHERE r.persoId = :persoId " +
            "ORDER BY r.dateCreation")
    List<Reclamation> findAllByPersoIdOrderByDate(@Param("persoId") String persoId);

    @Query("""
            SELECT 
                SUM(CASE WHEN r.responseRec IS NOT NULL THEN 1 ELSE 0 END) AS repondues,
                SUM(CASE WHEN r.responseRec IS NULL THEN 1 ELSE 0 END) AS nonRepondues,
                COUNT(r) AS total
            FROM Reclamation r
            WHERE r.persoId = :persoId 
            AND YEAR(r.dateCreation) = :annee
           """)
    Object[] statistiquesReclamations(@Param("persoId") String persoId,
                                      @Param("annee") int annee);

    @Query("SELECT r FROM Reclamation r WHERE r.persoId = :persoId AND YEAR(r.dateCreation) = :annee")
    List<Reclamation> findByPersoIdAndYear(@Param("persoId") String persoId, @Param("annee") Integer annee);
}

