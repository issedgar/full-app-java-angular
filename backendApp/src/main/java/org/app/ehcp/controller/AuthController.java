package org.app.ehcp.controller;

import org.app.ehcp.domain.dto.ClientLoginDTO;
import org.app.ehcp.domain.dto.response.ClientLoginResponseDTO;
import org.app.ehcp.service.interfaces.ClientService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final ClientService clientService;

    public AuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/login")
    public ClientLoginResponseDTO login(@Valid @RequestBody ClientLoginDTO clientLoginDTO) {
        return clientService.login(clientLoginDTO);
    }

}
