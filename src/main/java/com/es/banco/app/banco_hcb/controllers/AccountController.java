package com.es.banco.app.banco_hcb.controllers;

import java.util.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.es.banco.app.banco_hcb.dtos.requests.CreateAccountDTO;
import com.es.banco.app.banco_hcb.dtos.responses.AccountCreatedDTO;
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

    /* @PostMapping("/client/{idClient}/new_account")
    public ResponseEntity<AccountCreatedDTO> save(
            @RequestBody(required = true) CreateAccountDTO accountDTO,
            @PathVariable UUID idClient,
            HttpServletRequest request) {

        log.info("RAW BODY:");
        try (BufferedReader reader = request.getReader()) {
            reader.lines().forEach(line -> log.info(">> {}", line));
        } catch (Exception e) {
            log.error("Error leyendo input", e);
        }

        log.info("DTO recibido: {}", accountDTO);

        AccountCreatedDTO response = accountService.save(accountDTO, idClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } */

    
    @PostMapping("/client/{idClient}/new_account")
    public ResponseEntity<AccountCreatedDTO> save(@Valid @RequestBody CreateAccountDTO accountDTO, @PathVariable UUID idClient) {
        log.info("Se inicia registro de nueva tarjeta para el cliente con el id {} ", idClient);
        log.info("DTO recibido: {}", accountDTO);
        AccountCreatedDTO response = accountService.save(accountDTO, idClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }  
}
