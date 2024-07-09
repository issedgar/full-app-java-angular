package org.app.ehcp.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.app.ehcp.domain.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetail implements UserDetails {

    private final String username;
    private final Long clientId;
    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetail(String username, Long clientId, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.clientId = clientId;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetail build(Client client) {
        return new CustomUserDetail(
                client.getUsername(),
                client.getId(),
                client.getPassword(),
                Collections.emptyList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
