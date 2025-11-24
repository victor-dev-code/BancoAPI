package com.es.banco.app.banco_hcb.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.es.banco.app.banco_hcb.dtos.requests.CreateAccountDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.mapper.AccountMapper;
import com.es.banco.app.banco_hcb.model.*;
import com.es.banco.app.banco_hcb.repositories.*;
import com.es.banco.app.banco_hcb.services.interfaces.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountCreatedDTO save(CreateAccountDTO accountDTO, UUID idClient) {
        log.info("Se inicia el proceso de registro de una nueva cuenta para el usuario {}", idClient);
        
        Account account = accountMapper.toEntity(accountDTO);
       
        Client client = clientRepository.findById(idClient).get();
        account.setClient(client);
        log.info("Se obtiene el cliente solicitado para realizar el registro ");
       
        if (clientRepository.existsByRfcOrCurp(client.getRfc(), client.getCurp())) {
           log.info("El cliente {} no esta registrado en la base de datos.", idClient);
           throw new RuntimeException("No esta registrado el cliente en la base de datos.");            
        }

        if (accountDTO.getBalance() == null) {
            log.info("No se cuenta con ningun saldo en la cuenta.");
            account.setBalance(BigDecimal.ZERO);
        }

        Account accountSaved = accountRepository.save(account);
        log.info("Se ha registrado una nueva cuenta para el cliente {}.", idClient);
        return accountMapper.toDTO(accountSaved);
    }

    @Override
    public Optional<AccountResponseDTO> findByNumber(String number) {
        return null;
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts(UUID idClient) {
        return null;
    }

    @Override
    public Boolean updateStatus() {
        return null;
    }

    
}
