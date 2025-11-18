package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.StatActAnn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StatActAnnRepository extends JpaRepository<StatActAnn, Long> {

    @Query("SELECT DISTINCT s.annee FROM StatActAnn s ORDER BY s.annee DESC")
    List<Integer> findAllDistinctAnnees();

    @Query("SELECT DISTINCT s.codeSociete FROM StatActAnn s ORDER BY s.codeSociete")
    List<String> findAllDistinctSocietes();

    @Query("SELECT s.acte, SUM(s.depense), SUM(s.rembourser) " +
            "FROM StatActAnn s " +
            "WHERE s.annee = :annee AND s.codeSociete = :codeSociete " +
            "GROUP BY s.acte ORDER BY SUM(s.depense) DESC")
    List<Object[]> findStatistiquesActesByAnneeAndSociete(
            @Param("annee") Integer annee,
            @Param("codeSociete") String codeSociete
    );

    @Query("SELECT SUM(s.depense), SUM(s.rembourser), COUNT(DISTINCT s.acte) " +
            "FROM StatActAnn s " +
            "WHERE s.annee = :annee AND s.codeSociete = :codeSociete")
    List<Object[]> findStatistiquesGlobales(
            @Param("annee") Integer annee,
            @Param("codeSociete") String codeSociete
    );

    @Query("SELECT s FROM StatActAnn s " +
            "WHERE s.annee = :annee AND s.codeSociete = :codeSociete " +
            "ORDER BY s.acte")
    List<StatActAnn> findByAnneeAndSociete(
            @Param("annee") Integer annee,
            @Param("codeSociete") String codeSociete
    );
}
