package com.controle.ponto.webapp.contollers.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.dto.user.UserResponseDTO;
import com.controle.ponto.webapp.interfaces.IContoller;
import com.controle.ponto.services.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Users")
public class UserController implements IContoller<UserRequestDTO> {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity findAll(){
        var users = userService.findAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        var user = userService.findById(id);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid UserRequestDTO data){
        UserResponseDTO user = userService.post(data);
        URI location = URI.create("/user/" + user.getId());

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid UserRequestDTO data){
        UserResponseDTO user = userService.put(data);

        return ResponseEntity.accepted().body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patch(@RequestBody UserRequestDTO data, @PathVariable String id){

        UserResponseDTO user = userService.patch(data, id);

        return ResponseEntity.accepted().body(null);
    }

}
