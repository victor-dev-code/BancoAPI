package com.es.banco.app.banco_hcb.dtos.responses;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientWithAccountsDTO {
    
    private String fullname;
    private String curp;
    private String rfc;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    private String phone;
    private List<AccountsDTO> accounts;

    
}
