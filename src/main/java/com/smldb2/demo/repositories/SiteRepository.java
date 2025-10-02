package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, String> {
    List<Site> findByCodeEntite(String codeEntite);
    Optional<Site> findByDesSit(String desSit);
}
