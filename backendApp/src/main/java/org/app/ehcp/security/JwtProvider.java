package org.app.ehcp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Component
public class JwtProvider {
    private final String secretKey;
    private final long expirationMilliseconds;


    public JwtProvider(
            @Value("${security.jwt.token.secret-key}") String secretKey,
            @Value("${security.jwt.token.expiration}") long expirationMilliseconds) {
        this.secretKey = secretKey;
        this.expirationMilliseconds = expirationMilliseconds;
    }

    public String generateToken(String username, Long clientId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("clientId", clientId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationMilliseconds))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return getPayload(token).getBody().getSubject();
    }

    public Long getClientId(String token){
        return getPayload(token).getBody().get("clientId", Long.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public Boolean validateToken(String token){
        return (Objects.nonNull(getUsername(token)) && !isTokenExpired(token));
    }

    public Date getExpirationDate(String token) {
        return this.getPayload(token).getBody().getExpiration();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private JwtParserBuilder getJwtParserBuilder() {
        return parserBuilder().setSigningKey(getKey());
    }

    private Jws<Claims> getPayload(String token) {
        return getJwtParserBuilder().build().parseClaimsJws(token);
    }
}
