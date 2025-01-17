package com.controle.ponto.services.company;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.dto.company.CompanyResponseDTO;
import com.controle.ponto.exceptions.BadRequestCustomException;
import com.controle.ponto.exceptions.NotFoundCustomException;
import com.controle.ponto.interfaces.services.IService;
import com.controle.ponto.repositories.company.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService implements IService<CompanyRequestDTO, CompanyResponseDTO> {

    @Autowired
    private CompanyRepository repository;

    public List<CompanyResponseDTO> findAll(){
        var companies = repository.findAll();

        List<CompanyResponseDTO> companyList = new ArrayList<>();

        for (Company company : companies){
            CompanyResponseDTO companyDTO = new CompanyResponseDTO(company);
            companyList.add(companyDTO);
        }
        return companyList;
    }

    public CompanyResponseDTO findById(String id){
        Optional<Company> company = repository.findById(id);

        if (!company.isPresent()){
            throw new NotFoundCustomException("Empresa não encontrada!");
        }

        Company foundCompany = company.get();

        return new CompanyResponseDTO(foundCompany);
    }

    public CompanyResponseDTO post(CompanyRequestDTO data){
        Optional<Company> company = Optional.ofNullable(repository.findByName(data.getName()));
        if (company.isPresent()){
            throw new BadRequestCustomException("Empresa ja cadastrada!");
        }

        Company newCompany = new Company(data);
        repository.save(newCompany);

        return new CompanyResponseDTO(newCompany);
    }

    @Transactional
    public CompanyResponseDTO put(CompanyRequestDTO data){
        Optional<Company> company = repository.findById(data.getId());
        if (!company.isPresent()){
            throw new NotFoundCustomException("Empresa não encontrada!");
        }

        Company foundCompany = company.get();
        foundCompany.setName(data.getName());
        foundCompany.setAddress(data.getAddress());
        foundCompany.setTelephone(data.getTelephone());

        return new CompanyResponseDTO(foundCompany);
    }
}
