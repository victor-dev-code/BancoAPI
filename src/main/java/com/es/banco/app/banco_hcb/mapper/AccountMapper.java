package com.es.banco.app.banco_hcb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.es.banco.app.banco_hcb.dtos.requests.CreateAccountDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.model.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(CreateAccountDTO accountDTO);
    
    AccountCreatedDTO toDTO(Account account);

    @Mapping(source = "client.fullname", target = "fullname")
    AccountResponseDTO toResponseDTO(Account account);

    ClientWithAccountsDTO toClientWithAccountsDTO(Client client);
}
