package com.es.banco.app.banco_hcb.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.es.banco.app.banco_hcb.model.Account;
import com.es.banco.app.banco_hcb.model.enums.AccountTypeCodeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {
    private String number;
    private AccountTypeCodeEnum accountType;
    private BigDecimal balance;
    private boolean isActive;
    private LocalDateTime createdAt;
    private Account client;
}
