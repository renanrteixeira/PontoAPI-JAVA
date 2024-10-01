package com.controle.ponto.contollers.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.user.User;
import com.controle.ponto.exceptions.BadRequestCustomException;
import com.controle.ponto.exceptions.user.UserNotFoundException;
import com.controle.ponto.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id){
        var user = service.findById(id);
        if (user == null){
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity postUser(@RequestBody @Valid UserRequestDTO data){
        User user = service.postUser(data);
        if (user.getId() == null){
            throw new BadRequestCustomException("Usuário já cadastrado.");
        }
        URI location = URI.create("/user/" + user.getId());

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping
    public ResponseEntity putUser(@RequestBody @Valid UserRequestDTO data){
        User user = service.putUser(data);
        if (user == null){
            throw new UserNotFoundException();
        }

        return ResponseEntity.accepted().body(user);
    }
}
