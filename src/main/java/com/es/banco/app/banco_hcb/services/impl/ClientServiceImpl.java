package com.es.banco.app.banco_hcb.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.es.banco.app.banco_hcb.dtos.requests.*;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.exceptions.UserAlreadyExistsException;
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
        log.info("Se busca dentro de la base de datos si el cliente {} solicitado ya existe", clientDTO.getName());

        if (clientRepository.existsByRfcOrCurp(clientDTO.getRfc(), clientDTO.getCurp())) {
            log.error("El cliente ya se encuentra registrado en la base de datos, el RFC o CURP ya estan en el sistema");
            throw new UserAlreadyExistsException("El cliente ya se encuentra registrado en la base de datos, el RFC o CURP ya estan en el sistema");
        }

        Client client = clientMapper.toEntity(clientDTO);
        Client clientSaved = clientRepository.save(client);
        log.info("Se ha registrado un nuevo cliente.");
        return clientMapper.toDTO(clientSaved);    
    }

    @Override
    public Optional<ClientResponseDTO> getById(UUID id) {
        log.info("Se busca la informacion de cliente con el id {}", id );
        return clientRepository.findById(id).map(
            clientMapper::responseDTO
        );
    }

    @Override
    public List<ClientResponseDTO> getByFullname(String fullname) {
        log.info("Se busca la informacion de cliente con el nombre {}", fullname );
        return clientRepository.getAllClientsByFullname(fullname).stream().map(
            clientMapper::responseDTO
        ).collect(Collectors.toList());
    }
    
    @Override
    public List<ClientResponseDTO> getAllClients() {
        log.info("Se obtiene la informacion de todos los clientes registrados en el sistema" );
        return clientRepository.findAll().stream().map(
            clientMapper::responseDTO
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<ClientResponseDTO> updateClient(UpdateClientDTO clientDTO) {
        return null;
    }

	@Override
	public boolean existsById(UUID id) {
		return clientRepository.existsById(id);
	}
    
}
