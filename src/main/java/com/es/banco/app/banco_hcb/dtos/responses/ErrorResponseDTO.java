package com.es.banco.app.banco_hcb.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private int status;
    private String error;
    private String mesagge;
}
