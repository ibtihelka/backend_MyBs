package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Bordereau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BordereauRepository extends JpaRepository<Bordereau, String> {

    /**
     * Trouver un bordereau par sa référence
     */
    Optional<Bordereau> findByRefBordereau(String refBordereau);


    /**
     * Trouver les bordereaux dont la référence contient une chaîne
     */
    List<Bordereau> findByRefBordereauContaining(String keyword);

    /**
     * Compter le nombre de bordereaux par préfixe
     */
    @Query("SELECT COUNT(b) FROM Bordereau b WHERE b.refBordereau LIKE CONCAT(:prefix, '%')")
    long countByPrefix(@Param("prefix") String prefix);

    /**
     * Récupérer les bordereaux avec au moins un remboursement
     */
    @Query("SELECT DISTINCT b FROM Bordereau b LEFT JOIN FETCH b.remboursements WHERE SIZE(b.remboursements) > 0")
    List<Bordereau> findBordereauxWithRemboursements();

    /**
     * Récupérer les bordereaux d'une entreprise spécifique
     */

    /**
     * Compter le nombre total de remboursements pour un bordereau
     */
    @Query("SELECT COUNT(r) FROM Bordereau b JOIN b.remboursements r WHERE b.refBordereau = :refBordereau")
    long countRemboursementsByBordereau(@Param("refBordereau") String refBordereau);

    /**
     * Obtenir la somme des montants dépensés pour un bordereau
     */
    @Query("SELECT COALESCE(SUM(r.mntBs), 0) FROM Bordereau b JOIN b.remboursements r WHERE b.refBordereau = :refBordereau")
    Double sumMontantDepenseByBordereau(@Param("refBordereau") String refBordereau);

    /**
     * Obtenir la somme des montants remboursés pour un bordereau
     */
    @Query("SELECT COALESCE(SUM(r.mntBsRemb), 0) FROM Bordereau b JOIN b.remboursements r WHERE b.refBordereau = :refBordereau")
    Double sumMontantRembourseeByBordereau(@Param("refBordereau") String refBordereau);

    // Trouver les bordereaux par préfixe (ex: STAFIM, TP, etc.)
    List<Bordereau> findByRefBordereauStartingWith(String prefix);

    // Trouver les bordereaux par code entreprise
    @Query("SELECT b FROM Bordereau b JOIN b.remboursements r WHERE r.codeEntreprise = :code")
    List<Bordereau> findByCodeEntreprise(@Param("code") String codeEntreprise);

    // Trouver les bordereaux par site
    @Query("SELECT DISTINCT b FROM Bordereau b JOIN b.remboursements r WHERE r.site = :site")
    List<Bordereau> findBySite(@Param("site") String site);


}