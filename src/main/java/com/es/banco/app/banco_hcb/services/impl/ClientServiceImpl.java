package com.es.banco.app.banco_hcb.services.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.es.banco.app.banco_hcb.dtos.requests.*;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.exceptions.*;
import com.es.banco.app.banco_hcb.mapper.ClientMapper;
import com.es.banco.app.banco_hcb.model.*;
import com.es.banco.app.banco_hcb.repositories.*;
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
    public ClientSavedDTO updateClient(UpdateClientDTO clientDTO, UUID id) {
        log.info("Se busca el usuario en la base de datos.");
        return clientRepository.findById(id).map(
            clientDB -> {
                log.info("Guardando información del cliente {} en la base de datos.", id);
                clientDB.setBirthdate(clientDTO.getBirthdate());
                clientDB.setPhone(clientDTO.getPhone());
                clientRepository.save(clientDB);
                log.info("Se ha terminado el proceso de manera exitosa.");
                return clientMapper.toDTO(clientDB);
            }
        ).orElseThrow(
            () -> new ClientNotFoundException("Cliente "+ id +" no se ha encontrado en la base de datos.")
        ); 
    }

    @Override
    public ClientSavedDTO changeDisabledStatus(UUID id) {
        log.info("Buscando la informacion del cliente {}.", id);

        Client client = clientRepository.findById(id).orElseThrow(
            () -> new ClientNotFoundException("Cliente no se ha encontrado en la base de datos.")
        );

        log.info("Se encuentra la informacion del cliente {}.", client.getFullname());
        
        if(!client.isActive()) {
            log.warn("El cliente {} ya esta inactivo.", client.getFullname());
            throw new IllegalStateException("El cliente " + client.getFullname() + " ya no se encuentra activo en el banco.");
        }
        
        log.info("Verificando las cuentas del cliente {}.", client.getFullname());

        List<Account> accounts = client.getAccounts();
        
        boolean hasNonZeroBalance = 
            accounts.stream().anyMatch(account -> account.getBalance().compareTo(BigDecimal.ZERO) != 0);
        
        if (hasNonZeroBalance) {
            log.info("El cliente {} tiene alguna cuenta con saldo activo.", client.getFullname());
            throw new IllegalStateException("Alguna cuenta todavia tiene con saldo activo, la cuenta debe estar en $0 MXN y no contar con saldo pendiente.");

        }

        accounts.forEach(account -> {
            log.info("Desactivando cuenta {}...", account.getNumber());
            account.setActive(false);
        });
            
        log.info("Todas las cuentas se han desactivado. Procediendo a desactivar cliente. ");


        client.setActive(false);
        clientRepository.save(client);
        
        log.info("El cliente {} se ha desactivado correctamente.", client.getFullname());
        return clientMapper.toDTO(client);
    }
    
}
