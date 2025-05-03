package com.controle.ponto.domain.dto.user;

import com.controle.ponto.domain.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserResponseDTO {

    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private char admin;
    @NotNull
    private char status;

    public UserResponseDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.admin = user.getAdmin();
        this.status = user.getStatus();
    }
}
