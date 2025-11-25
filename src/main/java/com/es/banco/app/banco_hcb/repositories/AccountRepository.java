package com.es.banco.app.banco_hcb.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.es.banco.app.banco_hcb.model.*;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    //List<Account> findAllByClient(Client client); 
    Optional<Account> findByNumber(String number);   
}
