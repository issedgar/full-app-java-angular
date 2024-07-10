package org.app.ehcp.service.interfaces;

import org.app.ehcp.domain.dto.ClientCreateDTO;
import org.app.ehcp.domain.dto.ClientLoginDTO;
import org.app.ehcp.domain.dto.ClientUpdateDTO;
import org.app.ehcp.domain.dto.response.ClientLoginResponseDTO;
import org.app.ehcp.domain.dto.response.ClientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    ClientResponseDTO get(Long clientId);
    ClientLoginResponseDTO login(ClientLoginDTO clientLoginDTO);
    ClientResponseDTO create(ClientCreateDTO clientCreateDTO);
    ClientResponseDTO update(Long clientId, ClientUpdateDTO clientUpdateDTO);
    void delete(Long clientId);
    Page<ClientResponseDTO> findByPage(Pageable pageable);
    List<ClientResponseDTO> findAll();
}
