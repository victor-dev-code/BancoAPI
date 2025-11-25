package com.es.banco.app.banco_hcb.services.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.es.banco.app.banco_hcb.dtos.requests.CreateAccountDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.model.Account;
import com.es.banco.app.banco_hcb.model.Client;

public interface AccountService {
    AccountCreatedDTO save(CreateAccountDTO accountDTO, UUID idClient);
    Optional<AccountResponseDTO> findByNumber(String number);
    List<AccountResponseDTO> getAllAccounts(UUID idClient);
    Boolean updateStatus();
    Account addZeroBalance(Account account, CreateAccountDTO createdDTO);
    void validateClientExists(Client client);
}
