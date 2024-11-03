package com.controle.ponto.domain.dto.company;

import com.controle.ponto.domain.company.Company;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CompanyResponseDTO {
    @NotNull
    String id;

    @NotNull
    String name;

    @NotNull
    String address;

    @NotNull
    String telephone;

    public CompanyResponseDTO(Company company){
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.telephone = company.getTelephone();
    }
}
