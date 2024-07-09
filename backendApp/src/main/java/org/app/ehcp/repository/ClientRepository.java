package org.app.ehcp.repository;

import org.app.ehcp.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);
    Optional<Client> findByUsernameIgnoreCase(String username);
}
