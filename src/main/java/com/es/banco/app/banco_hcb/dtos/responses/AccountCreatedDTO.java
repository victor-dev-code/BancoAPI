package com.es.banco.app.banco_hcb.dtos.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreatedDTO {
    private UUID id;
    private String number;
}
