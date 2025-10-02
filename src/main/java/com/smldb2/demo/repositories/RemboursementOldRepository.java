package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.RemboursementOld;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemboursementOldRepository extends JpaRepository<RemboursementOld, String> {
    List<RemboursementOld> findByPersoId(String persoId);
}
