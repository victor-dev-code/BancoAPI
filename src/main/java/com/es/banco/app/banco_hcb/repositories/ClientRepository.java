package com.es.banco.app.banco_hcb.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.es.banco.app.banco_hcb.model.Client;
import java.util.List;



public interface ClientRepository extends JpaRepository<Client, UUID> {
    List<Client> getAllClientsByFullname(String fullname);
    boolean existsByRfc(String rfc);
    boolean existsByCurp(String curp);
}
