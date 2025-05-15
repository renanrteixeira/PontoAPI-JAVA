package com.controle.ponto.domain.dto.role;

import com.controle.ponto.domain.role.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RoleResponseDTO {
    @NotNull
    private String id;
    @NotNull
    private String name;

    public RoleResponseDTO(Role role){
        this.id = role.getId();
        this.name = role.getName();
    }
}
