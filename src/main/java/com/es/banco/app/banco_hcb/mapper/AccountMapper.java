package com.es.banco.app.banco_hcb.mapper;

import org.mapstruct.Mapper;

import com.es.banco.app.banco_hcb.dtos.requests.CreateAccountDTO;
import com.es.banco.app.banco_hcb.dtos.responses.*;
import com.es.banco.app.banco_hcb.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(CreateAccountDTO accountDTO);
    
    AccountCreatedDTO toDTO(Account account);
    AccountResponseDTO toResponseDTO(Account account);
}
