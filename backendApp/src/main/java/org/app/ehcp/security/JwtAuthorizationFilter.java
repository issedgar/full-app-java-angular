package org.app.ehcp.security;

import org.app.ehcp.service.interfaces.UserDetailsSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer";
    @Autowired
    private UserDetailsSecurityService userDetailsSecurityService;
    @Autowired
    private JwtProvider jwtProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtAuthorizationFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    private Optional<String> getBearerToken(String authorizationHeader) {
        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
            return Optional.of(authorizationHeader.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = ((HttpServletRequest) request).getHeader(AUTHORIZATION);
        try {
            if (Objects.nonNull(authorizationHeader)) {
                getBearerToken(authorizationHeader)
                        .flatMap(this.userDetailsSecurityService::loadUserByJwtToken)
                        .ifPresent(userDetails -> {
                            SecurityContextHolder.getContext().setAuthentication(
                                    new PreAuthenticatedAuthenticationToken(
                                            userDetails, "", userDetails.getAuthorities())
                            );
                        });
            }
            filterChain.doFilter(request, response);
        }catch (Exception ex){
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
