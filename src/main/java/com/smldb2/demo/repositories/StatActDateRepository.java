package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.StatActDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StatActDateRepository extends JpaRepository<StatActDate, Long> {

    // Récupérer la liste des codes société distincts
    @Query("SELECT DISTINCT s.codeSociete FROM StatActDate s ORDER BY s.codeSociete")
    List<String> findAllDistinctCodeSociete();

    // Récupérer les adhérents avec remboursements par période et société
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

    // Récupérer les actes d'un adhérent par période et société
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

    // Statistiques globales par période et société
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

    // Statistiques par adhérent
    @Query("SELECT s.matricule as matricule, " +
            "SUM(s.depense) as totalDepense, " +
            "SUM(s.rembourse) as totalRembourse, " +
            "COUNT(s) as nombreActes " +
            "FROM StatActDate s " +
            "WHERE s.codeSociete = :codeSociete " +
            "AND s.dateBs BETWEEN :dateDebut AND :dateFin " +
            "GROUP BY s.matricule " +
            "ORDER BY SUM(s.depense) DESC")
    List<Object[]> findStatistiquesParAdherent(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );
}