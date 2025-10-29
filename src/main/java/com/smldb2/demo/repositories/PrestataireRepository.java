package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Prestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestataireRepository extends JpaRepository<Prestataire, String> {
    Optional<Prestataire> findByPersoIdAndPersoPassed(String persoId, String persoPassed);
}
