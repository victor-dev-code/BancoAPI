package com.es.banco.app.banco_hcb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.es.banco.app.banco_hcb.dtos.requests.CreateClientDTO;
import com.es.banco.app.banco_hcb.dtos.responses.ClientSavedDTO;
import com.es.banco.app.banco_hcb.services.impl.ClientServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/save_new_client")
    public ResponseEntity<ClientSavedDTO> save(@RequestBody CreateClientDTO clientDTO) {
        ClientSavedDTO response = clientService.save(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
