package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.ActeDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ActeDateRepository extends JpaRepository<ActeDate, Long> {

    // Récupérer les statistiques groupées par adhérent pour une période et société
    @Query("SELECT a.matricule, " +
            "COUNT(DISTINCT a.dateBs) as nombreBs, " +
            "SUM(a.depense) as totalDepense, " +
            "SUM(a.rembourse) as totalRembourse " +
            "FROM ActeDate a " +
            "WHERE a.codeSociete = :codeSociete " +
            "AND a.dateBs BETWEEN :dateDebut AND :dateFin " +
            "GROUP BY a.matricule")
    List<Object[]> getStatistiquesAdherents(
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );

    // Récupérer tous les actes d'un adhérent pour une période et société
    @Query("SELECT a FROM ActeDate a " +
            "WHERE a.matricule = :matricule " +
            "AND a.codeSociete = :codeSociete " +
            "AND a.dateBs BETWEEN :dateDebut AND :dateFin " +
            "ORDER BY a.dateBs DESC")
    List<ActeDate> getActesByAdherent(
            @Param("matricule") String matricule,
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );

    // Récupérer les statistiques groupées par acte médical
    @Query("SELECT a.acte, " +
            "SUM(a.depense) as totalDepense, " +
            "SUM(a.rembourse) as totalRembourse, " +
            "COUNT(a) as nombre " +
            "FROM ActeDate a " +
            "WHERE a.matricule = :matricule " +
            "AND a.codeSociete = :codeSociete " +
            "AND a.dateBs BETWEEN :dateDebut AND :dateFin " +
            "GROUP BY a.acte")
    List<Object[]> getStatistiquesActesByAdherent(
            @Param("matricule") String matricule,
            @Param("codeSociete") String codeSociete,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin
    );
}