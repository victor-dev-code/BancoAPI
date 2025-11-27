package com.es.banco.app.banco_hcb.dtos.requests;

import java.math.BigDecimal;

import com.es.banco.app.banco_hcb.model.enums.AccountTypeCodeEnum;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDTO {
    @NotNull(message = "Se debe seleccionar el tipo de cuenta.")
    private AccountTypeCodeEnum accountType;
    private BigDecimal balance;
}
