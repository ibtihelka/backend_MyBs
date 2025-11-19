package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.Tel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelRepository extends JpaRepository<Tel, Integer> {

    List<Tel> findByPersoId(String persoId);
    List<Tel> findByExported(String exported);
    List<Tel> findByPersoIdAndExported(String persoId, String exported);

}
