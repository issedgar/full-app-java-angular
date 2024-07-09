package org.app.ehcp.service.interfaces;

import org.app.ehcp.security.CustomUserDetail;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserDetailsSecurityService extends UserDetailsService {
    Optional<CustomUserDetail> loadUserByJwtToken(String jwtToken);
}
