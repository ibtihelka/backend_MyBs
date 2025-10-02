package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Rib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RibRepository extends JpaRepository<Rib, Integer> {
    List<Rib> findByPersoId(String persoId);
    List<Rib> findByExported(String exported);
}
