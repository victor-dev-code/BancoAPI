package com.es.banco.app.banco_hcb.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.es.banco.app.banco_hcb.model.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    
}
