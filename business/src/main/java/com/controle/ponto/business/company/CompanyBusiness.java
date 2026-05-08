package com.controle.ponto.business.company;

import com.controle.ponto.domain.entity.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.dto.company.CompanyResponseDTO;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.NotFoundCustomException;
import com.controle.ponto.domain.mappers.company.CompanyMapper;
import com.controle.ponto.persistence.company.CompanyRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CompanyBusiness {

    private static final Logger logger = LoggerFactory.getLogger(CompanyBusiness.class);

    @Autowired
    private CompanyRepository repository;

    public List<CompanyResponseDTO> findAll(){
        logger.info("Buscando todas as empresas");
        var companies = repository.findAll();

        List<CompanyResponseDTO> companyList = new ArrayList<>();

        for (Company company : companies){
            CompanyResponseDTO companyDTO = CompanyMapper.INSTANCE.toResponseDTO(company);
            companyList.add(companyDTO);
        }
        logger.info("Encontradas {} empresas", companyList.size());
        return companyList;
    }

    public CompanyResponseDTO findById(String id){
        logger.info("Buscando empresa por ID {}", id);
        Optional<Company> company = repository.findById(id);

        if (!company.isPresent()){
            logger.warn("Empresa não encontrada: {}", id);
            throw new NotFoundCustomException("Empresa não encontrada!");
        }

        Company foundCompany = company.get();
        logger.info("Empresa encontrada: {}", id);

        return CompanyMapper.INSTANCE.toResponseDTO(foundCompany);
    }

    public CompanyResponseDTO post(CompanyRequestDTO data){
        logger.info("Criando nova empresa {}", data.getName());
        Optional<Company> company = Optional.ofNullable(repository.findByName(data.getName()));
        if (company.isPresent()){
            logger.warn("Tentativa de criar empresa duplicada: {}", data.getName());
            throw new BadRequestCustomException("Empresa ja cadastrada!");
        }

        Company newCompany = new Company(data);
        repository.save(newCompany);
        logger.info("Empresa criada com sucesso");

        return CompanyMapper.INSTANCE.toResponseDTO(newCompany);
    }

    @Transactional
    public CompanyResponseDTO put(CompanyRequestDTO data){
        logger.info("Atualizando empresa {}", data.getId());
        Optional<Company> company = repository.findById(data.getId());
        if (!company.isPresent()){
            logger.warn("Empresa não encontrada para atualização: {}", data.getId());
            throw new NotFoundCustomException("Empresa não encontrada!");
        }

        Company foundCompany = company.get();
        foundCompany.setName(data.getName());
        foundCompany.setAddress(data.getAddress());
        foundCompany.setTelephone(data.getTelephone());
        logger.info("Empresa atualizada com sucesso");

        return CompanyMapper.INSTANCE.toResponseDTO(foundCompany);
    }
}
