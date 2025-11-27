package com.es.banco.app.banco_hcb.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import com.es.banco.app.banco_hcb.model.*;


public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findAllByClient(Client client); 
    //List<Account> findAllByBalanceGreaterThan(BigDecimal balance);
    Optional<Account> findByNumber(String number);   
}
