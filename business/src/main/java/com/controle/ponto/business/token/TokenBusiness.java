package com.controle.ponto.business.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.controle.ponto.domain.dto.token.TokenRequestDTO;
import com.controle.ponto.domain.exceptions.user.UserNotFoundException;
import com.controle.ponto.domain.entity.user.UserAuthenticated;
import com.controle.ponto.persistence.user.UserRepository;
import com.controle.ponto.resources.config.HashPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TokenBusiness {

    private static final Logger logger = LoggerFactory.getLogger(TokenBusiness.class);

    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private UserRepository repository;

    public String generateToken(UserAuthenticated user){
        logger.info("Gerando token para usuário {}", user.getUsername());
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
            logger.info("Token gerado com sucesso");
            return token;
        } catch (JWTCreationException exception) {
            logger.error("Error while generating token", exception);
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token){
        logger.debug("Validando token");
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            logger.warn("Invalid token: {}", token);
            return "";
        }
    }

    public UserAuthenticated getUser(TokenRequestDTO data){
        logger.info("Tentativa de login para usuário {}", data.username());
        var user = repository.findByUsername(data.username());
        if (user.isEmpty()){
            logger.warn("User not found: {}", data.username());
            throw new UserNotFoundException();
        }

        HashPassword hash = new HashPassword();
        boolean isValid = hash.IsValidPassword(data.password(), user.get().getPassword());
        if (!isValid){
            logger.warn("Invalid password for user: {}", data.username());
            throw new UserNotFoundException();
        }

        logger.info("Login bem-sucedido para usuário {}", data.username());
        return new UserAuthenticated(user.get());
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
