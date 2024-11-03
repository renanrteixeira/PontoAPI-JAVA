package com.controle.ponto.domain.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

@Table(name="roles")
@Entity(name="roles")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    @OneToOne(mappedBy = "role")
    private Employee employee;

    public Role(RoleRequestDTO role){
        this.id = role.getId();
        this.name = role.getName();
    }
}
