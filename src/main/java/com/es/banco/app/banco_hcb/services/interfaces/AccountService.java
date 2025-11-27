package com.es.banco.app.banco_hcb.services.interfaces;

import java.util.*;

import com.es.banco.app.banco_hcb.dtos.requests.CreateAccountDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.model.*;

public interface AccountService {
    AccountCreatedDTO save(CreateAccountDTO accountDTO, UUID idClient);
    Optional<AccountResponseDTO> findByNumber(String number);
    Optional<ClientWithAccountsDTO> getAllAccounts(UUID idClient);
    
    AccountCreatedDTO changeDisabledStatus(String number);
    AccountCreatedDTO changeActiveStatus(String number);
    void hasNonZeroBalance(Account account);

    Account addZeroBalance(Account account, CreateAccountDTO createdDTO);
    void validateClientExists(Client client);
    Account createAccountNumber(Account account);
    Account generateClabe(Account account);
    int calculateCheckDigit(String clabe);
}
