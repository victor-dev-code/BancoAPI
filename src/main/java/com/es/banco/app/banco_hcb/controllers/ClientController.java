package com.es.banco.app.banco_hcb.controllers;

import java.util.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.es.banco.app.banco_hcb.dtos.requests.CreateClientDTO;
import com.es.banco.app.banco_hcb.dtos.requests.UpdateClientDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.exceptions.ClientNotFoundException;
import com.es.banco.app.banco_hcb.services.impl.ClientServiceImpl;

import jakarta.validation.Valid;
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
    public ResponseEntity<ClientSavedDTO> save(@Valid @RequestBody CreateClientDTO clientDTO) {
        log.info("Se obtiene la informacion proporcionada por el nuevo cliente {} ", clientDTO);
        ClientSavedDTO response = clientService.save(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ClientResponseDTO> getById(@PathVariable UUID id) {
        log.info("Se busca dentro de la base de datos la informacion del cliente {} solicitado", id);
        Optional<ClientResponseDTO> clientDTO = clientService.getById(id);
        return clientDTO.map(
            ResponseEntity::ok
        ).orElseThrow(
            () -> new ClientNotFoundException("Cliente no se ha encontrado en la base de datos.")
        );
    }

    @GetMapping("/client")
    public ResponseEntity<List<ClientResponseDTO>> getByfullname(@RequestParam String name) {
        log.info("Se busca dentro de la base de datos la informacion del cliente {} solicitado", name);
        List<ClientResponseDTO> clientDTO = clientService.getByFullname(name);
        if (clientDTO.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(clientDTO);
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        log.info("Se busca dentro de la base de datos la informacion de los usuarios.");
        List<ClientResponseDTO> clientDTO = clientService.getAllClients();
        if (clientDTO.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(clientDTO);
    }

    @PatchMapping("/client/{id}")
    public ResponseEntity<ClientSavedDTO> updateClient(@Valid @RequestBody UpdateClientDTO clientDTO, @PathVariable UUID id) {
        log.info("Se obtiene la informacion proporcionada por el cliente {} ", clientDTO);
        ClientSavedDTO response = clientService.updateClient(clientDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/client/{id}/disabled")
    public ResponseEntity<ClientSavedDTO> changeDisabledStatus(@PathVariable UUID id) {
        log.info("Iniciando el cambio de estatus del cliente {} ", id);
        ClientSavedDTO response = clientService.changeDisabledStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/client/{id}/active")
    public ResponseEntity<ClientSavedDTO> changeActiveStatus(@PathVariable UUID id) {
        log.info("Iniciando el cambio de estatus del cliente {} ", id);
        ClientSavedDTO response = clientService.changeActiveStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    
}
