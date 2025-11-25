package com.es.banco.app.banco_hcb.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.es.banco.app.banco_hcb.model.Client;
import java.util.List;
import java.util.UUID;




public interface ClientRepository extends JpaRepository<Client, UUID> {
    List<Client> getAllClientsByFullname(String fullname);
    boolean existsByRfcOrCurp(String rfc, String curp);
    boolean existsById(UUID id);
}
