package uz.pdp.todo_project.config.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import uz.pdp.todo_project.entity.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;


@Component
public class JwtTokenUtil {


    private final SecretKey secretKey;
    private final Long expiration;

    public JwtTokenUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") Long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }


    public String generateToken(User user) {

        List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        Date now = new Date();

        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("roles", roles)
                .signWith(secretKey)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration))
                .compact();
    }


    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean validateToken(String token) {

        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public String extractUsername(String token) {
        return parseToken(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<SimpleGrantedAuthority> extractAuthorities(String token) {

        List<String> roles = parseToken(token).get("roles", List.class);

        return roles.stream().map(SimpleGrantedAuthority::new).toList();

    }






}
