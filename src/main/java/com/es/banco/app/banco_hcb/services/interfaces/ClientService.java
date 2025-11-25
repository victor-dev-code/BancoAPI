package com.es.banco.app.banco_hcb.services.interfaces;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.es.banco.app.banco_hcb.dtos.requests.*;
import com.es.banco.app.banco_hcb.dtos.responses.*;

public interface ClientService {
    ClientSavedDTO save(CreateClientDTO clientDTO);
    Optional<ClientResponseDTO> getById(UUID id);
    List<ClientResponseDTO> getByFullname(String fullname);
    List<ClientResponseDTO> getAllClients();
    Optional<ClientResponseDTO> updateClient(UpdateClientDTO clientDTO);
}
