package com.es.banco.app.banco_hcb.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.es.banco.app.banco_hcb.dtos.requests.*;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.mapper.ClientMapper;
import com.es.banco.app.banco_hcb.model.Client;
import com.es.banco.app.banco_hcb.repositories.ClientRepository;
import com.es.banco.app.banco_hcb.services.interfaces.ClientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientSavedDTO save(CreateClientDTO clientDTO) {
        log.info("Se inicia la creacion de un nuevo cliente con el nombre {}", clientDTO.getName());
        Client client = clientMapper.toEntity(clientDTO);
        Client clientSaved = clientRepository.save(client);
        return clientMapper.toDTO(clientSaved);    
    }

    @Override
    public Optional<ClientResponseDTO> getById(UUID id) {
        return null;
    }

    @Override
    public Optional<ClientResponseDTO> getByFullname(String fullname) {
        return null;
    }

    @Override
    public List<ClientResponseDTO> getAllUsers() {
        return null;
    }

    @Override
    public Optional<ClientResponseDTO> updateClient(UpdateClientDTO clientDTO) {
        return null;
    }
    
}
