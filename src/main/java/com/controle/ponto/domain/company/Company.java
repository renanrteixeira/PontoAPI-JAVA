package com.controle.ponto.domain.company;

import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "companies")
@Entity(name = "companies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String name;

    String address;

    String telephone;

    @OneToOne(mappedBy = "company")
    private Employee employee;

    public Company(CompanyRequestDTO company){
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.telephone = company.getTelephone();
    }

}