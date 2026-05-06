package com.controle.ponto.domain.dto.user;

import com.controle.ponto.domain.entity.enums.AdminStatus;
import com.controle.ponto.domain.entity.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserRequestDTO {

        private String id;
        @NotNull
        private String name;
        @NotNull
        private String email;
        @NotNull
        private String username;
        @NotNull
        private String password;

        private AdminStatus admin;

        private UserStatus status;

}
