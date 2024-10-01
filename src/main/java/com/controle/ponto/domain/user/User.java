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
        this.name = userRequest.getName();
        this.email = userRequest.getEmail();
        this.username = userRequest.getUsername();
        this.password = userRequest.getPassword();
        this.admin = userRequest.getAdmin();
        this.status = userRequest.getStatus();
    }

}
