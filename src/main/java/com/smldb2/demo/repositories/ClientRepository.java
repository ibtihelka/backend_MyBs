package com.smldb2.demo.repositories;
import com.smldb2.demo.Entity.Bssaisis;
import com.smldb2.demo.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {


    Optional<Client> findByCodeClt(String codeClt);
}

