package com.controle.ponto.domain.mappers.company;

import com.controle.ponto.domain.entity.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.dto.company.CompanyResponseDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);


//    @Mapping( target = "id", source = "id", ignore = true)
    Company toEntity(CompanyRequestDTO companyRequestDTO);

    Company toEntity(CompanyResponseDTO companyResponseDTO);

    @InheritInverseConfiguration
    CompanyRequestDTO toRequestDTO(Company company);

    @InheritInverseConfiguration
    CompanyResponseDTO toResponseDTO(Company company);

}
