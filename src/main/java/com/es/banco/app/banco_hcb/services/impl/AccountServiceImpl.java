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
        createAccountNumber(account);
        generateClabe(account);
        addZeroBalance(account, accountDTO);
        
        account.setClient(client);

        Account accountSaved = accountRepository.save(account);
        log.info("Se ha registrado una nueva cuenta para el cliente {}.", client.getFullname());
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
    public AccountCreatedDTO changeDisabledStatus(String number) {
        log.info("Buscando la informacion de la cuenta {}.", number);
        Account account = accountRepository.findByNumber(number).orElseThrow(
            () -> new ClientNotFoundException("No se ha encontrado la cuenta con el numero " + number + " en la base de datos.")
        );

        log.info("Se encuentra la informacion de la cuenta {}.", account.getNumber());

        if (!account.isActive()) {
            log.warn("La cuenta {} ya esta inactiva.", account.getNumber());
            throw new IllegalStateException("La cuenta " + account.getNumber() + " ya no se encuentra activa en el banco.");
        }

        hasNonZeroBalance(account);
        
        account.setActive(false);
        accountRepository.save(account);

        log.info("La cuenta {} se ha desactivado correctamente.", account.getNumber());

        return accountMapper.toDTO(account);
    }

    @Override
    public AccountCreatedDTO changeActiveStatus(String number) {
        log.info("Buscando la informacion de la cuenta {}.", number);
        Account account = accountRepository.findByNumber(number).orElseThrow(
            () -> new ClientNotFoundException("No se ha encontrado la cuenta con el numero " + number + " en la base de datos.")
        );
        log.info("Se encuentra la informacion de la cuenta {}.", account.getNumber());

        if (account.isActive()) {
            log.warn("La cuenta {} ya esta activa.", account.getNumber());
            throw new IllegalStateException("La cuenta " + account.getNumber() + " ya esta activa.");
        }

        account.setActive(true);
        accountRepository.save(account);

        log.info("La cuenta {} se ha activado correctamente.", account.getNumber());
        return accountMapper.toDTO(account);
    }

    @Override
    public void hasNonZeroBalance(Account account) {
        boolean hasNonZeroBalance = account.getBalance().compareTo(BigDecimal.ZERO) != 0;
        if (hasNonZeroBalance) {
            log.warn("La cuenta con numero {} tiene saldo activo.", account.getNumber());
            throw new IllegalStateException("La cuenta tiene saldo activo de " + account.getBalance() + ". La cuenta debe estar en $0.00 MXN y no contar con saldo pendiente.");
        }
        log.info("La cuenta no tiene saldo pendiente. Se procede a desactivarla.");
    }

    
    //Metodos para generar una nueva cuenta
    @Override
    public void validateClientExists(Client client) {
        boolean existsClient = clientRepository.existsByRfcOrCurp(client.getRfc(), client.getCurp());

        if (!existsClient) {
            log.warn("No se cuenta con la informacion de registro del cliente con el id {} en la base de datos.", client.getId());
            throw new ClientNotFoundException("No esta registrada la informacion del cliente en la base de datos.");
        }
        log.info("Se ha encontrado la informacion del cliente {} en la base de datos del sistema.", client.getFullname());
    }

    @Override
    public Account createAccountNumber(Account account) {
        log.info("Generando numero de cuenta...");
        Long secuencia = accountRepository.count() + 1;
        String accountNumber = String.format("%011d", secuencia);
        
        account.setNumber(accountNumber);
        log.info("Se crea y se almacena el numero de cuenta {} de forma exitosa.", account.getNumber());
        return account;
    }

    @Override
    public Account generateClabe(Account account) {
        log.info("Generando el numero de CLABE de la cuenta {}", account.getNumber());
        String bancoId = "032";
        String clabe = bancoId + "010" + account.getNumber();
        
        int dv = calculateCheckDigit(clabe);
        clabe += dv;

        account.setClabe(clabe);
        log.info("Se genera satisfactoriamente y se almacena el numero de CLABE {}.", clabe);
        return account;
    }

    @Override
    public Account addZeroBalance(Account account, CreateAccountDTO accountDTO) {
        if (accountDTO.getBalance() == null) {
            log.info("La cuenta del cliente actualmente tiene el saldo de $0.00 MXN.");
            account.setBalance(BigDecimal.ZERO);
        }
        return account;
    }

    @Override
    public int calculateCheckDigit(String clabe) {
        log.info("Calculando el digito de control");
        int[] factores = {3, 7, 1};
        int suma = 0;
        for (int i = 0; i < 17; i++){
            int digito = Character.getNumericValue(clabe.charAt(i));
            int factor = factores[i % 3];
            suma += (digito * factor) % 10;
        }
        return  (10 - (suma % 10)) % 10;
    }

}
