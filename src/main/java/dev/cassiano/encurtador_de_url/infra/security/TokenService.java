package dev.cassiano.encurtador_de_url.infra.security;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;


@Service
public class TokenService {
    
    @Value("${secret_key}")
    private String secretKey;

    @Value("${spring.application.name}")
    private String appName;

    public String generateToken(String email) {   
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withSubject(email)
                    .withIssuer(appName)
                    .withExpiresAt(OffsetDateTime.now().plusHours(2).toInstant())
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }   
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(appName)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
