package com.controle.ponto.domain.entity.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "role")
    private List<Employee> employee;

    public Role(RoleRequestDTO role){
        this.id = role.getId();
        this.name = role.getName();
    }
}
