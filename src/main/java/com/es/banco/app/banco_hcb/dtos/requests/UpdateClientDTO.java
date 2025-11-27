package com.es.banco.app.banco_hcb.dtos.requests;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de cumpleaños no debe estar vacia.")
    private LocalDate birthdate;
    @NotBlank(message = "El numero de telefono no debe estar vacio.")
    private String phone;
}
