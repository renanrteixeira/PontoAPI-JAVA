package com.controle.ponto.contollers.token;

import com.controle.ponto.domain.dto.message.MessageResponseDTO;
import com.controle.ponto.domain.dto.token.TokenReponseDTO;
import com.controle.ponto.domain.dto.token.TokenRequestDTO;
import com.controle.ponto.exceptions.user.UserNotFoundException;
import com.controle.ponto.services.token.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    TokenService service;

    @PostMapping
    public ResponseEntity getToken(@RequestBody @Valid TokenRequestDTO data){
        String token = service.getToken(data);
        if (token == null){
            throw new UserNotFoundException();
//            MessageResponseDTO message = new MessageResponseDTO(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), "Usuário ou senha inválidos!");
//
//            return ResponseEntity.badRequest().body(message);
        }

        TokenReponseDTO tokenReponse = new TokenReponseDTO(token);

        return ResponseEntity.ok(tokenReponse);
    }
}
