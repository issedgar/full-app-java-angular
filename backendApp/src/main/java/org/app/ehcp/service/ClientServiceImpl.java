package org.app.ehcp.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.app.ehcp.domain.Client;
import org.app.ehcp.domain.dto.ClientCreateDTO;
import org.app.ehcp.domain.dto.ClientLoginDTO;
import org.app.ehcp.domain.dto.ClientUpdateDTO;
import org.app.ehcp.domain.dto.response.ClientLoginResponseDTO;
import org.app.ehcp.domain.dto.response.ClientResponseDTO;
import org.app.ehcp.repository.ClientRepository;
import org.app.ehcp.security.JwtProvider;
import org.app.ehcp.service.interfaces.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository clientRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional(readOnly = true)
    public ClientLoginResponseDTO login(ClientLoginDTO clientLoginDTO) {
        Optional<Client> clientOptional = clientRepository.findByUsernameIgnoreCase(clientLoginDTO.getUsername());
        if(clientOptional.isEmpty()) {
            log.error("User not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        Client client = clientOptional.get();

        ClientLoginResponseDTO clientLoginResponseDTO = new ClientLoginResponseDTO();
        clientLoginResponseDTO.setClientId(client.getId());
        clientLoginResponseDTO.setUsername(client.getUsername());
        clientLoginResponseDTO.setFullName((client.getName() + " " + client.getLastName()).trim());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(client.getUsername(), clientLoginDTO.getPassword()));
            Optional<String> tokenOptional = Optional.of(jwtProvider.generateToken(client.getUsername(), client.getId()));
            clientLoginResponseDTO.setToken(tokenOptional.get());
            return clientLoginResponseDTO;

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Credentials not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credentials not found");
        }
    }

    @Override
    @Transactional
    public ClientResponseDTO create(ClientCreateDTO clientCreateDTO) {
        Optional<Client> clientOptional = clientRepository.findByUsernameIgnoreCase(clientCreateDTO.getUsername());
        if(clientOptional.isPresent()) {
            log.error("User exists");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exists");
        }

        Client client = ClientCreateDTO.convertToClient(clientCreateDTO);
        client.setUsername(clientCreateDTO.getUsername().toLowerCase());
        client.setPassword(passwordEncoder.encode(clientCreateDTO.getPassword()));

        Client clientSave = clientRepository.save(client);
        return ClientResponseDTO.convertToClientResponseDTO(clientSave);
    }

    @Override
    @Transactional
    public ClientResponseDTO update(Long clientId, ClientUpdateDTO clientUpdateDTO) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if(clientOptional.isEmpty()) {
            log.error("User not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        Client client = clientOptional.get();

        if(Strings.isNotEmpty(clientUpdateDTO.getName())) {
            client.setName(clientUpdateDTO.getName());
        }
        if(Strings.isNotEmpty(clientUpdateDTO.getLastName())) {
            client.setLastName(clientUpdateDTO.getLastName());
        }
        if(Strings.isNotEmpty(clientUpdateDTO.getAddress())) {
            client.setAddress(clientUpdateDTO.getAddress());
        }

        try {
            Client clientSave = clientRepository.save(client);
            return ClientResponseDTO.convertToClientResponseDTO(clientSave);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error update");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error update");
        }
    }

    @Override
    @Transactional
    public void delete(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if(clientOptional.isEmpty()) {
            log.error("User not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }

        try {
            clientRepository.deleteById(clientId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error delete");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error delete");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO get(Long clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if(clientOptional.isEmpty()) {
            log.error("User not found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        }
        return ClientResponseDTO.convertToClientResponseDTO(clientOptional.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> findByPage(Pageable pageable) {
        Page<Client> clientPage = clientRepository.findAll(pageable);
        List<ClientResponseDTO> clientDTOList = clientPage.stream()
                .map(ClientResponseDTO::convertToClientResponseDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(clientDTOList, pageable, clientPage.getTotalElements());
    }

    @Override
    public List<ClientResponseDTO> findAll() {
        List<Client> clientList = clientRepository.findAll();
        return clientList.stream()
                .map(ClientResponseDTO::convertToClientResponseDTO).toList();
    }
}
