package com.es.banco.app.banco_hcb.services.impl;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.stereotype.Service;

import com.es.banco.app.banco_hcb.dtos.requests.CreateAccountDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.exceptions.ClientNotFoundException;
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
       
        Client client = clientRepository.findById(idClient).orElseThrow(
            () -> new ClientNotFoundException("Cliente no se ha encontrado en la base de datos.")
        );
        
        log.info("Se obtiene el cliente solicitado para realizar el registro ");
        
        validateClientExists(client);
        addZeroBalance(account, accountDTO);
        
        account.setClient(client);

        Account accountSaved = accountRepository.save(account);
        log.info("Se ha registrado una nueva cuenta para el cliente {}.", idClient);
        return accountMapper.toDTO(accountSaved);
    }

    @Override
    public Optional<AccountResponseDTO> findByNumber(String number) {
        log.info("Se ha encontrado la informacion de la cuenta");
        return accountRepository.findByNumber(number).map(
            accountMapper::toResponseDTO
        );
    }

    @Override
    public Optional<ClientWithAccountsDTO> getAllAccounts(UUID idClient) {
        log.info("Buscando informacion del usuario...");
        Client client = clientRepository.findById(idClient)
            .orElseThrow(() -> new ClientNotFoundException("Cliente no se ha encontrado en la base de datos."));

        log.info("Se han encontrado la informacion de las cuentas del cliente {} registrado en la base de datos.", idClient);
        List<AccountsDTO> accounts = client.getAccounts().stream().map(
            account -> new AccountsDTO(
                account.getNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.isActive(),
                account.getCreatedAt()
            )).toList();
                
        log.info("Se encuentra la informacion del cliente {} registrado en la base de datos.", idClient);
        return Optional.of(new ClientWithAccountsDTO(
            client.getFullname(),
            client.getCurp(),
            client.getRfc(),
            client.getBirthdate(),
            client.getPhone(),
            accounts
        ));

    }

    @Override
    public Boolean updateStatus() {
        return null;
    }

    @Override
    public Account addZeroBalance(Account account, CreateAccountDTO accountDTO) {
        if (accountDTO.getBalance() == null) {
            log.info("La cuenta del cliente actualmente tiene el saldo cero pesos.");
            account.setBalance(BigDecimal.ZERO);
        }
        return account;
    }

    @Override
    public void validateClientExists(Client client) {
        boolean existsClient = clientRepository.existsByRfcOrCurp(client.getRfc(), client.getCurp());

        if (!existsClient) {
            log.warn("No se cuenta con la informacion de registro del cliente con el id {} en la base de datos.", client.getId());
            throw new ClientNotFoundException("No esta registrada la informacion del cliente en la base de datos.");
        }
        log.info("Se ha encontrado la informacion del cliente con id {} en la base de datos del sistema.", client.getId());
    }

}
