package com.smldb2.demo.repositories;



import com.smldb2.demo.Entity.Acte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActeRepository extends JpaRepository<Acte, Long> {

    // Trouver tous les actes d'un remboursement spécifique
    List<Acte> findByRefBsPhys(String refBsPhys);

    // Trouver les actes par code
    List<Acte> findByCodeActe(String codeActe);

    // Statistiques : Total des dépenses par code d'acte
    @Query("SELECT a.codeActe, SUM(a.depense), SUM(a.remboursement) " +
            "FROM Acte a GROUP BY a.codeActe ORDER BY SUM(a.depense) DESC")
    List<Object[]> getStatistiquesByCodeActe();

    // Statistiques pour un remboursement spécifique
    @Query("SELECT a FROM Acte a WHERE a.refBsPhys = :refBsPhys ORDER BY a.depense DESC")
    List<Acte> findActesByRemboursementOrderByDepense(@Param("refBsPhys") String refBsPhys);
}
