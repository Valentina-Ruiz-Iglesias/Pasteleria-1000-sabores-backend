package com.tienda.tienda_backend.security.jwt;

import com.tienda.tienda_backend.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // clave secreta simple para empezar (luego la movemos a application.properties)
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 1 hora de expiración
    private final long expirationMillis = 60 * 60 * 1000;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(user.getEmail())        // el "dueño" del token
                .claim("id", user.getId())
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .claim("isDuocStudent", user.getIsDuocStudent())
                .claim("hasFelices50", user.getHasFelices50())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    // nuevo: obtener email (subject) desde el token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
