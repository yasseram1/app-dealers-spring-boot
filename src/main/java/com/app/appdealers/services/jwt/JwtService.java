package com.app.appdealers.services.jwt;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.app.appdealers.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${security.jwt.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    public String generateToken(Usuario usuario, Map<String, Object> stringObjectMap) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issuedAt.getTime() + (EXPIRATION_MINUTES * 60 * 1000));

        return Jwts
                .builder()
                .subject(usuario.getUsername())
                .id(String.valueOf(usuario.getId()))
                .claims(stringObjectMap)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(getKey())
                .compact();
    }

    public String getUsernameFromToken(String jwt) {
        return getClaim(jwt, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Claims getAllClaims(String jwt) {
        return Jwts
                   .parser()
                   .verifyWith(getKey())
                   .build()
                   .parseSignedClaims(jwt)
                   .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolve) {
        final Claims claims = getAllClaims(token);
        return claimsResolve.apply(claims);
    }

    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    private SecretKey getKey() {
        byte[] secretAsBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretAsBytes);
    }


}
