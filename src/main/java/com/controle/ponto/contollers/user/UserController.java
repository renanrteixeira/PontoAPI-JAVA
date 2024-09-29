package com.controle.ponto.contollers.user;

import com.controle.ponto.domain.dto.message.MessageResponseDTO;
import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.user.User;
import com.controle.ponto.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity getAllUsers(){
        var users = service.findAll();

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity postUser(@RequestBody @Valid UserRequestDTO data){
        User user = service.postUser(data);
        if (user.getId() == null){
            MessageResponseDTO message = new MessageResponseDTO(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), "Usuário já cadastrado.");
            return ResponseEntity.badRequest().body(message);
        }
        URI location = URI.create("/users/" + user.getId());

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping
    public ResponseEntity putUser(@RequestBody @Valid UserRequestDTO data){
        User user = service.putUser(data);
        if (user == null){
            MessageResponseDTO message = new MessageResponseDTO(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), "Usuário não cadastrado.");
            return ResponseEntity.badRequest().body(message);
        }

        return ResponseEntity.accepted().body(user);
    }
}
