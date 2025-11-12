package com.smldb2.demo.repositories;

import com.smldb2.demo.Entity.User;
import com.smldb2.demo.Entity.UsersSociete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersSocieteRepository extends JpaRepository<UsersSociete, String> {

   List<UsersSociete> findByType(String type);
    Optional<UsersSociete> findByPersoId(String persoId);

    Optional<UsersSociete> findByPersoName(String name);
    Optional<UsersSociete> findByPersoIdAndPersoPassed(String persoId, String persoPassed);


}
