package com.controle.ponto.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;

import com.controle.ponto.domain.entity.enums.AdminStatus;
import com.controle.ponto.domain.entity.enums.UserStatus;
import com.controle.ponto.domain.converter.AdminStatusConverter;
import com.controle.ponto.domain.converter.UserStatusConverter;

@Table(name="users")
@Entity(name="users")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String email;

    private String username;

    private String password;

    @Convert(converter = AdminStatusConverter.class)
    private AdminStatus admin;

    @Convert(converter = UserStatusConverter.class)
    private UserStatus status;
}
