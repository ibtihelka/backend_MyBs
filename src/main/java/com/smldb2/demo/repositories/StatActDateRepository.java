package com.smldb2.demo.repositories;

import com.smldb2.demo.DTO.StatMedecinActeDTO;
import com.smldb2.demo.Entity.StatActDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatActDateRepository extends JpaRepository<StatActDate, Long> {

    @Query("SELECT DISTINCT s.codeSociete FROM StatActDate s ORDER BY s.codeSociete")
    List<String> findAllDistinctCodeSociete();

    @Query("SELECT DISTINCT s.matricule " +
            "FROM StatActDate s " +
            "WHERE s.dateBs BETWEEN :dateDebut AND :dateFin " +
            "AND s.codeSociete = :codeSociete " +
            "ORDER BY s.matricule")
    List<String> findMatriculesBySocieteAndPeriode(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    @Query("SELECT s FROM StatActDate s " +
            "WHERE s.matricule = :matricule " +
            "AND s.codeSociete = :codeSociete " +
            "AND s.dateBs BETWEEN :dateDebut AND :dateFin " +
            "ORDER BY s.dateBs DESC, s.acte")
    List<StatActDate> findActesByMatriculeAndPeriode(
            @Param("matricule") String matricule,
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    @Query("SELECT s.acte as acte, " +
            "SUM(s.depense) as totalDepense, " +
            "SUM(s.rembourse) as totalRembourse, " +
            "COUNT(s) as nombreActes " +
            "FROM StatActDate s " +
            "WHERE s.codeSociete = :codeSociete " +
            "AND s.dateBs BETWEEN :dateDebut AND :dateFin " +
            "GROUP BY s.acte " +
            "ORDER BY SUM(s.depense) DESC")
    List<Object[]> findStatistiquesActesBySocieteAndPeriode(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    /**
     * Statistiques par adhérent avec MAT et ADHEREN de la table remboursement
     * Jointure via NUM_BS = REF_BS_PHYS (clé de relation correcte)
     * Retourne: matricule, MAT, ADHEREN, RANG, depense, rembourse, nombreActes
     */
    @Query(value = "SELECT COALESCE(r.MAT, s.matricule) as matricule, " +
            "r.MAT as mat, " +
            "r.ADHEREN as nomAdherent, " +
            "r.RANG as rang, " +
            "SUM(s.depense) as totalDepense, " +
            "SUM(s.rembourse) as totalRembourse, " +
            "COUNT(s.id) as nombreActes " +
            "FROM stat_act_date s " +
            "LEFT JOIN remboursement r ON s.num_bs = r.REF_BS_PHYS " +
            "WHERE s.code_societe = :codeSociete " +
            "AND s.date_bs BETWEEN :dateDebut AND :dateFin " +
            "GROUP BY r.MAT, r.ADHEREN, r.RANG, s.matricule " +
            "ORDER BY SUM(s.depense) DESC",
            nativeQuery = true)
    List<Object[]> findStatistiquesParAdherent(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    /**
     * Statistiques par RANG (Type de bénéficiaire)
     * Sélectionne les remboursements de la période, puis compte par RANG
     * IMPORTANT: On travaille directement sur la table remboursement avec les filtres de période
     */
    @Query(value = "SELECT " +
            "    r.RANG as rang, " +
            "    COUNT(DISTINCT r.REF_BS_PHYS) as nombreRemboursements, " +
            "    COALESCE(SUM(s.depense), 0) as totalDepense, " +
            "    COALESCE(SUM(s.rembourse), 0) as totalRembourse " +
            "FROM remboursement r " +
            "LEFT JOIN stat_act_date s ON r.REF_BS_PHYS = s.num_bs " +
            "    AND s.code_societe = :codeSociete " +
            "    AND s.date_bs BETWEEN :dateDebut AND :dateFin " +
            "WHERE r.CodeEntreprise = :codeSociete " +
            "    AND r.DAT_BS BETWEEN :dateDebut AND :dateFin " +
            "    AND r.RANG IS NOT NULL " +
            "GROUP BY r.RANG " +
            "ORDER BY r.RANG",
            nativeQuery = true)
    List<Object[]> findStatistiquesParRang(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );


    /**
     * Statistiques par RIB pour une société et une période donnée
     */
    @Query(value = """
        SELECT 
            r.RIB as rib,
            COUNT(*) as nombre_remboursements,
            SUM(r.MNT_BS) as total_depense,
            SUM(r.MNT_BS_REMB) as total_rembourse
        FROM remboursement r
        WHERE r.CodeEntreprise = :codeSociete
          AND r.DAT_BS BETWEEN :dateDebut AND :dateFin
          AND r.RIB IS NOT NULL
        GROUP BY r.RIB
        ORDER BY total_depense DESC
        """, nativeQuery = true)
    List<Object[]> findStatistiquesParRib(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    @Query("SELECT s FROM StatActDate s WHERE s.numBs = :numBs AND s.mf IS NOT NULL")
    List<StatActDate> findValidStatByNumBs(@Param("numBs") String numBs);




    @Query(value =
            "SELECT s.acte AS acte, " +
                    "       COUNT(s.id) AS nombreActes, " +
                    "       COALESCE(m.DES, 'Non disponible') AS nomMedecin, " +
                    "       COALESCE(m.VILLE, 'Non disponible') AS villeMedecin, " +
                    "       SUM(s.depense) AS totalDepense, " +
                    "       SUM(s.rembourse) AS totalRembourse " +
                    "FROM stat_act_date s " +
                    "LEFT JOIN medecin m ON TRIM(s.COD_DOCT_ACT) = TRIM(m.COD_DOCT) " +
                    "WHERE s.code_societe = :codeSociete " +
                    "GROUP BY s.acte, s.COD_DOCT_ACT, m.DES, m.VILLE " +
                    "ORDER BY s.acte, COUNT(s.id) DESC",
            nativeQuery = true)
    List<Object[]> findTopMedecinParActeBySocieteNative(@Param("codeSociete") String codeSociete);

    /**
     * Récupère tous les codes société distincts
     */
    @Query("SELECT DISTINCT s.codeSociete FROM StatActDate s ORDER BY s.codeSociete")
    List<String> findAllCodeSociete();
}