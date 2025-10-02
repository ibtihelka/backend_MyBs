package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.UsersAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersAdminRepository extends JpaRepository<UsersAdmin, String> {

    Optional<UsersAdmin> findByPersoIdAndPersoPassed(String persoId, String persoPassed);
    Optional<UsersAdmin> findByPersoName(String persoName);

    List<UsersAdmin> findByType(String type);
    List<UsersAdmin> findBySite(String site);
}
