package com.es.banco.app.banco_hcb.mapper;

import org.mapstruct.*;

import com.es.banco.app.banco_hcb.dtos.requests.CreateClientDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "fullname", expression = "java(clientDTO.getName() + \" \" + clientDTO.getLastname())")
    Client toEntity(CreateClientDTO clientDTO);

    ClientSavedDTO toDTO(Client client);

    ClientResponseDTO responseDTO(Client client);
    
}
