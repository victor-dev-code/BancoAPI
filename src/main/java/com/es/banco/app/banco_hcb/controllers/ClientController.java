package com.es.banco.app.banco_hcb.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.es.banco.app.banco_hcb.dtos.requests.CreateClientDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.services.impl.ClientServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
        log.info("Se obtiene la informacion proporcionada por el cliente {} ", clientDTO);
        ClientSavedDTO response = clientService.save(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ClientResponseDTO> getById(@PathVariable UUID id) {
        log.info("Se busca dentro de la base de datos la informacion del cliente {} solicitado", id);
        Optional<ClientResponseDTO> clientDTO = clientService.getById(id);
        return clientDTO.map(
            ResponseEntity::ok
        ).orElseGet(
            () -> ResponseEntity.notFound().build()
        );
    }

    @GetMapping("client")
    public ResponseEntity<ClientResponseDTO> getByfullname(@RequestParam String name) {
        log.info("Se busca dentro de la base de datos la informacion del cliente {} solicitado", name);
        Optional<ClientResponseDTO> clientDTO = clientService.getByFullname(name);
        return clientDTO.map(
            ResponseEntity::ok
        ).orElseGet(
            () -> ResponseEntity.notFound().build()
        );
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientResponseDTO> clientDTO = clientService.getAllClients();
        if (clientDTO.isEmpty()) 
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(clientDTO);
    }
    
}
