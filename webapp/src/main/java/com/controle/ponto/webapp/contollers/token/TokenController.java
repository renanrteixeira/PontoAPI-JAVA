package com.controle.ponto.webapp.contollers.token;

import com.controle.ponto.domain.dto.token.TokenReponseDTO;
import com.controle.ponto.domain.dto.token.TokenRequestDTO;
import com.controle.ponto.services.token.TokenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@Slf4j
public class TokenController {

    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity getToken(@RequestBody @Valid TokenRequestDTO data){
        logger.info("Tentativa de login para usuário {}", data.username());
        var user = tokenService.getUser(data);

        var token = tokenService.generateToken(user);
        logger.info("Token gerado com sucesso para usuário {}", data.username());

        TokenReponseDTO tokenReponse = new TokenReponseDTO(token);

        return ResponseEntity.ok(tokenReponse);
    }
}
