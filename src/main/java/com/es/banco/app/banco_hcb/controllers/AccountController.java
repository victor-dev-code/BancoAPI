package com.es.banco.app.banco_hcb.controllers;

import java.util.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.es.banco.app.banco_hcb.dtos.requests.CreateAccountDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.exceptions.ClientNotFoundException;
import com.es.banco.app.banco_hcb.services.impl.AccountServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping("/client/{idClient}/new_account")
    public ResponseEntity<AccountCreatedDTO> save(@Valid @RequestBody CreateAccountDTO accountDTO, @PathVariable UUID idClient) {
        log.info("Se inicia registro de nueva tarjeta para el cliente con el id {} ", idClient);
        AccountCreatedDTO response = accountService.save(accountDTO, idClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/client/{idClient}")
    public ResponseEntity<ClientWithAccountsDTO> getAllAccounts(@PathVariable UUID idClient) {
        log.info("Inicianado proceso de busqueda...");
        Optional<ClientWithAccountsDTO> accounts = accountService.getAllAccounts(idClient);
        return accounts.map(
            ResponseEntity::ok
        ).orElseThrow(
            () -> new ClientNotFoundException("Cliente no se ha encontrado en la base de datos.")
        );
    }

    @GetMapping("/client/")
    public ResponseEntity<AccountResponseDTO> getAccount(@RequestParam String number) {
        log.info("Buscando información de la tarjeta con el numero {}...", number);
        Optional<AccountResponseDTO> accountDTO = accountService.findByNumber(number);
        return accountDTO.map(
            ResponseEntity::ok
        ).orElseThrow(
            () -> new ClientNotFoundException("La cuenta con el numero" + number + " no existe en el sistema.")
        );
    }

    @PatchMapping("/account/disabled")
    public ResponseEntity<AccountCreatedDTO> changeDisabledStatus(@RequestParam String number) {
        log.info("Iniciando el cambio de estatus de la cuenta {} ", number);
        AccountCreatedDTO response = accountService.changeDisabledStatus(number);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/account/active")
    public ResponseEntity<AccountCreatedDTO> changeActiveStatus(@RequestParam String number) {
        log.info("Iniciando el cambio de estatus de la cuenta {} ", number);
        AccountCreatedDTO response = accountService.changeActiveStatus(number);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
}
