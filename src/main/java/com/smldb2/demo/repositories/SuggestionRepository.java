package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
    List<Suggestion> findByPersoId(String persoId);
    List<Suggestion> findByExported(String exported);
}
