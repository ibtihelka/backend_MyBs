package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Famille;
import com.smldb2.demo.Entity.TypePrestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface FamilleRepository extends JpaRepository<Famille, String> {
    List<Famille> findByPersoId(String persoId);

    List<Famille> findByTypPrestataire(TypePrestataire typPrestataire);


}
