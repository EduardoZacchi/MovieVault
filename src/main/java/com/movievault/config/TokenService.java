package com.movievault.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.movievault.controller.response.JwtTokenResponse;
import com.movievault.controller.response.UserResponse;
import com.movievault.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenService {

    @Value("${movievault.security.secret}")
    private String secret;

    public String generateToken(User user){
        Algorithm alg = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId" , user.getId())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("MovieVault API")
                .sign(alg);
    }

    public Optional<JwtTokenResponse> validateToken(String token){
        try {
            Algorithm alg = Algorithm.HMAC256(secret);

            DecodedJWT decode = JWT.require(alg)
                    .build()
                    .verify(token);

            return Optional.of(JwtTokenResponse.builder()
                    .email(decode.getSubject())
                    .id(decode.getClaim("userId").asLong())
                    .build());

        } catch (JWTVerificationException e){
            return Optional.empty();
        }
    }
}
