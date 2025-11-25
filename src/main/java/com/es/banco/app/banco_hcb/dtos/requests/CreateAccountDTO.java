package com.es.banco.app.banco_hcb.dtos.requests;

import java.math.BigDecimal;

import com.es.banco.app.banco_hcb.model.enums.AccountTypeCodeEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDTO {
    @NotBlank(message = "El numero de tarjeta no debe estar vacio")
    private String number;
    private AccountTypeCodeEnum accountType;
    private BigDecimal balance;
}
