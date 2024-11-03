package com.controle.ponto.domain.employee;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "employees")
@Entity(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    Date admission;

    char gender;

    char status;

//    String roleId;

//    String companyId;

//    @OneToOne
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_Id")
    Role role;

    @OneToOne(fetch = FetchType.LAZY)
//    @OneToOne
    @JoinColumn(name = "company_Id")
    Company company;

}
