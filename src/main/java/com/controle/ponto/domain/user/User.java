package com.controle.ponto.domain.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name="users")
@Entity(name="users")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String email;

    private String username;

    private String password;

    private char admin;

    private char status;

    public  User(UserRequestDTO userRequest){
        this.name = userRequest.name();
        this.email = userRequest.email();
        this.username = userRequest.username();
        this.password = userRequest.password();
        this.admin = userRequest.admin();
        this.status = userRequest.status();
    }

}
