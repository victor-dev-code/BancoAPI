package com.es.banco.app.banco_hcb.dtos.requests;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientDTO {
    @NotBlank(message = "El nombre es obligatorio.")
    private String name;
    @NotBlank(message = "Los apellidos son obligatorios.")
    private String lastname;
    
    @NotBlank(message = "El CURP es obligatorio.")
    @Size(min = 18, max = 18, message = "El CURP debe tener 18 caracteres.")
    private String curp;
    
    @NotBlank(message = "El RFC es obligatorio.")
    @Size(min = 13, max = 13, message = "El RFC debe contener 13 caracteres.")
    private String rfc;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento debe ser una del pasado.")
    private LocalDate birthdate;
    
    @NotBlank(message = "El número de celular es obligatorio.")
    @Pattern(
        regexp = "\\d{2}-\\d{2}-\\d{2}-\\d{2}-\\d{2}", 
        message = "El número de celular debe contener el formato 11-22-33-44-55"
    )
    private String phone;
}
