package org.app.ehcp.service;

import org.app.ehcp.domain.Client;
import org.app.ehcp.repository.ClientRepository;
import org.app.ehcp.security.CustomUserDetail;
import org.app.ehcp.security.JwtProvider;
import org.app.ehcp.service.interfaces.UserDetailsSecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
public class UserDetailsSecurityServiceImpl implements UserDetailsSecurityService {

    private final ClientRepository clientRepository;
    private final JwtProvider jwtProvider;

    public UserDetailsSecurityServiceImpl(ClientRepository clientRepository, JwtProvider jwtProvider) {
        this.clientRepository = clientRepository;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public Optional<CustomUserDetail> loadUserByJwtToken(String jwtToken) {
        if (jwtProvider.validateToken(jwtToken)) {
            return Optional.of(new CustomUserDetail(jwtProvider.getUsername(jwtToken), jwtProvider.getClientId(jwtToken), "", Collections.emptyList() ));
        }
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = this.clientRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("user no exist"));

        return withUsername(client.getUsername())
                .password(client.getPassword())
                .authorities("ADMIN")
                .credentialsExpired(false)
                .accountLocked(false)
                .accountExpired(false)
                .disabled(false)
                .build();
    }
}
