package com.es.banco.app.banco_hcb.dtos.responses;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {
    private String fullname;
    private String curp;
    private String rfc;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String phone;
}
