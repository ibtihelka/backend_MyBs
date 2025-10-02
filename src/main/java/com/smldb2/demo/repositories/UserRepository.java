package com.smldb2.demo.repositories;


import com.smldb2.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByPersoName(String persoName);
    List<User> findByEmailContaining(String email);
    List<User> findBySexe(String sexe);


     Optional<User> findByPersoIdAndPersoPassed(String persoId, String persoPassed);


    /**
     * Trouver un utilisateur par email
     */
    Optional<User> findByEmail(String email);

    /**
     * Trouver un utilisateur par CIN
     */
    Optional<User> findByCin(String cin);


    /**
     * Alternative si vous n'avez pas de champ dateCreation :
     * Vous pouvez utiliser datNais (date de naissance) ou tout autre champ Date
     * Exemple avec datePieceIdentite :
     */
    // long countByDatePieceIdentiteAfter(Date startDate);


    long countByDateCreationAfter(Date date);
}

