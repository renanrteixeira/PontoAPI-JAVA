package com.controle.ponto.webapp.contollers.token;

import com.controle.ponto.domain.dto.token.TokenReponseDTO;
import com.controle.ponto.domain.dto.token.TokenRequestDTO;
import com.controle.ponto.services.token.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity getToken(@RequestBody @Valid TokenRequestDTO data){
        var user = tokenService.getUser(data);

        var token = tokenService.generateToken(user);

        TokenReponseDTO tokenReponse = new TokenReponseDTO(token);

        return ResponseEntity.ok(tokenReponse);
    }
}
