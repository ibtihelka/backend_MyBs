package com.smldb2.demo.repositories;

import com.smldb2.demo.DTO.MatStatDTO;
import com.smldb2.demo.Entity.Remboursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RemboursementRepository extends JpaRepository<Remboursement, String> {

    List<Remboursement> findByPersoId(String persoId);
    List<Remboursement> findByStatBs(String statBs);
    List<Remboursement> findByRefBordereau(String refBordereau);
    Optional<Remboursement> findByRefBsPhys(String refBsPhys);
    List<Remboursement> findByPersoIdAndStatBs(String persoId, String statBs);

    // ✅ Compter les remboursements avec cod_doct_cv non null
    @Query("SELECT COUNT(r) FROM Remboursement r WHERE r.persoId = :persoId AND r.codDoctCv IS NOT NULL")
    Long countRemboursementsWithCodDoctCv(@Param("persoId") String persoId);

    // ✅ Récupérer tous les codes entreprise distincts
    @Query(value = "SELECT DISTINCT CodeEntreprise FROM remboursement WHERE CodeEntreprise IS NOT NULL ORDER BY CodeEntreprise", nativeQuery = true)
    List<String> findAllDistinctCodeEntreprise();

    // ✅ Statistiques par rang
    @Query(value = """
        SELECT 
            r.RANG as rang,
            COUNT(DISTINCT r.REF_BS_PHYS) as nombreRemboursements,
            COALESCE(SUM(r.MNT_BS), 0) as totalDepense,
            COALESCE(SUM(r.MNT_BS_REMB), 0) as totalRembourse,
            COUNT(a.REF_BS_PHYS) as nombreActes
        FROM remboursement r
        LEFT JOIN acte a ON r.REF_BS_PHYS = a.REF_BS_PHYS
        WHERE r.CodeEntreprise = :codeSociete
        AND r.DAT_BS BETWEEN :dateDebut AND :dateFin
        AND r.RANG IS NOT NULL
        GROUP BY r.RANG
        ORDER BY 
            CASE 
                WHEN r.RANG = '0' THEN 1
                WHEN r.RANG IN ('90', '98') THEN 2
                ELSE 3
            END,
            CAST(r.RANG AS UNSIGNED)
        """, nativeQuery = true)
    List<Object[]> findStatistiquesParRang(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );

    // ✅ MÉTHODE MANQUANTE : Récupérer les remboursements par rang
    @Query(value = """
        SELECT r.*
        FROM remboursement r
        WHERE r.CodeEntreprise = :codeSociete
        AND r.DAT_BS BETWEEN :dateDebut AND :dateFin
        AND r.RANG = :rang
        ORDER BY r.DAT_BS DESC, r.REF_BS_PHYS
        """, nativeQuery = true)
    List<Remboursement> findRemboursementsByRang(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin,
            @Param("rang") String rang
    );

    @Query(value = """
    SELECT 
        COUNT(DISTINCT r.REF_BS_PHYS) as nombreTotalRemboursements,
        COUNT(a.REF_BS_PHYS) as nombreTotalActes,
        COALESCE(SUM(r.MNT_BS), 0) as montantTotalDepense,
        COALESCE(SUM(r.MNT_BS_REMB), 0) as montantTotalRembourse
    FROM remboursement r
    LEFT JOIN acte a ON r.REF_BS_PHYS = a.REF_BS_PHYS
    WHERE r.CodeEntreprise = :codeSociete
    AND r.DAT_BS BETWEEN :dateDebut AND :dateFin
    """, nativeQuery = true)
    Object[] findStatistiquesGenerales(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );


    // Récupérer les MF répétitifs par société et période avec le nombre de remboursements et total remboursé
    @Query(value = """
    SELECT r.MF, COUNT(r.REF_BS_PHYS) AS nombreRemboursements, COALESCE(SUM(r.MNT_BS_REMB),0) AS totalRembourse
    FROM remboursement r
    WHERE r.CodeEntreprise = :codeSociete
    AND r.DAT_BS BETWEEN :dateDebut AND :dateFin
    GROUP BY r.MF
    HAVING COUNT(r.REF_BS_PHYS) > 1
    ORDER BY nombreRemboursements DESC
    """, nativeQuery = true)
    List<Object[]> findStatistiquesParMf(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );


    // Ajoutez cette méthode dans RemboursementRepository.java

    /**
     * Récupérer les statistiques par MAT (matricule prestataire)
     * Retourne tous les MAT avec leur nombre de remboursements et totaux
     */
    @Query(value = """
    SELECT 
        r.MAT as mat,
        COUNT(r.REF_BS_PHYS) as nombreRemboursements,
        COALESCE(SUM(r.MNT_BS), 0) as totalDepense,
        COALESCE(SUM(r.MNT_BS_REMB), 0) as totalRembourse
    FROM remboursement r
    WHERE r.CodeEntreprise = :codeSociete
    AND r.DAT_BS BETWEEN :dateDebut AND :dateFin
    AND r.MAT IS NOT NULL
    GROUP BY r.MAT
    ORDER BY nombreRemboursements DESC
    """, nativeQuery = true)
    List<Object[]> findStatistiquesParMat(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );

    /**
     * Récupérer les remboursements détaillés pour un MAT spécifique
     */
    @Query(value = """
    SELECT r.*
    FROM remboursement r
    WHERE r.CodeEntreprise = :codeSociete
    AND r.DAT_BS BETWEEN :dateDebut AND :dateFin
    AND r.MAT = :mat
    ORDER BY r.DAT_BS DESC, r.REF_BS_PHYS
    """, nativeQuery = true)
    List<Remboursement> findRemboursementsByMat(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin,
            @Param("mat") String mat
    );


    /**
     * Récupérer les statistiques par COD_DOCT_CV
     * Retourne tous les COD_DOCT_CV avec leur nombre de remboursements et totaux
     */
    @Query(value = """
    SELECT 
        r.COD_DOCT_CV as codDoctCv,
        COUNT(r.REF_BS_PHYS) as nombreRemboursements,
        COALESCE(SUM(r.MNT_BS), 0) as totalDepense,
        COALESCE(SUM(r.MNT_BS_REMB), 0) as totalRembourse
    FROM remboursement r
    WHERE r.CodeEntreprise = :codeSociete
    AND r.DAT_BS BETWEEN :dateDebut AND :dateFin
    AND r.COD_DOCT_CV IS NOT NULL
    GROUP BY r.COD_DOCT_CV
    ORDER BY nombreRemboursements DESC
    """, nativeQuery = true)
    List<Object[]> findStatistiquesParCodDoctCv(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );

    /**
     * Récupérer les remboursements détaillés pour un COD_DOCT_CV spécifique
     */
    @Query(value = """
    SELECT r.*
    FROM remboursement r
    WHERE r.CodeEntreprise = :codeSociete
    AND r.DAT_BS BETWEEN :dateDebut AND :dateFin
    AND r.COD_DOCT_CV = :codDoctCv
    ORDER BY r.DAT_BS DESC, r.REF_BS_PHYS
    """, nativeQuery = true)
    List<Remboursement> findRemboursementsByCodDoctCv(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin,
            @Param("codDoctCv") String codDoctCv
    );
    // ✅ NOUVELLE MÉTHODE : Récupérer les remboursements avec cod_doct_cv non null
    @Query("SELECT r FROM Remboursement r WHERE r.persoId = :persoId AND r.codDoctCv IS NOT NULL ORDER BY r.datBs DESC")
    List<Remboursement> findRemboursementsWithCodDoctCv(@Param("persoId") String persoId);

    @Query("SELECT r.refBsPhys FROM Remboursement r WHERE r.persoId = :persoId")
    List<String> findRefBsByPersoId(@Param("persoId") String persoId);

    List<Remboursement> findByPersoIdAndRefBsPhys(String persoId, String refBsPhys);

    // Nouvelles méthodes pour les statistiques

    @Query("SELECT COUNT(r) FROM Remboursement r WHERE r.persoId = :persoId")
    long countByPersoId(@Param("persoId") String persoId);

    @Query("SELECT COUNT(r) FROM Remboursement r WHERE r.persoId = :persoId " +
            "AND YEAR(r.datBs) = :annee")
    long countByPersoIdAndYear(@Param("persoId") String persoId,
                               @Param("annee") Integer annee);



    @Query("SELECT COUNT(r) FROM Remboursement r WHERE r.persoId = :persoId " +
            "AND r.codDoctCv IS NOT NULL AND r.codDoctCv != '' " +
            "AND YEAR(r.datBs) = :annee")
    long countContreVisitesByPersoIdAndYear(@Param("persoId") String persoId,
                                            @Param("annee") Integer annee);
    @Query("""
            SELECT COUNT(r)
            FROM Remboursement r
            WHERE r.persoId = :persoId
            AND r.codDoctCv IS NOT NULL
            AND YEAR(r.datBs) = :annee
           """)
    Long countContreVisites(@Param("persoId") String persoId,
                            @Param("annee") int annee);

    @Query("SELECT r FROM Remboursement r WHERE r.persoId = :persoId AND YEAR(r.datBs) = :annee")
    List<Remboursement> findByPersoIdAndYear(@Param("persoId") String persoId, @Param("annee") Integer annee);
}

