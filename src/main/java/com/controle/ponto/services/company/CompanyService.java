package com.controle.ponto.services.company;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.exceptions.BadRequestCustomException;
import com.controle.ponto.exceptions.NotFoundCustomException;
import com.controle.ponto.repositories.company.CompanyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository repository;

    public List<Company> findAll(){
        var companies = repository.findAll();

        return companies;
    }

    public Company findById(String id){
        Optional<Company> company = repository.findById(id);

        if (!company.isPresent()){
            throw new NotFoundCustomException("Empresa não encontrada!");
        }

        Company foundCompany = company.get();

        return foundCompany;
    }

    public Company post(CompanyRequestDTO data){
        Optional<Company> company = Optional.ofNullable(repository.findByName(data.getName()));
        if (company.isPresent()){
            throw new BadRequestCustomException("Empresa ja cadastrada!");
        }

        Company newCompany = new Company(data);
        repository.save(newCompany);

        return newCompany;
    }

    @Transactional
    public Company put(CompanyRequestDTO data){
        Optional<Company> company = repository.findById(data.getId());
        if (!company.isPresent()){
            throw new NotFoundCustomException("Empresa não encontrada!");
        }

        Company foundCompany = company.get();
        foundCompany.setName(data.getName());
        foundCompany.setAddress(data.getAddress());
        foundCompany.setTelephone(data.getTelephone());

        return foundCompany;
    }
}
