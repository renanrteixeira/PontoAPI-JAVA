package com.controle.ponto.services.company;

import com.controle.ponto.business.company.CompanyBusiness;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.dto.company.CompanyResponseDTO;
import com.controle.ponto.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService implements IService<CompanyRequestDTO, CompanyResponseDTO> {

    @Autowired
    private CompanyBusiness companyBusiness;

    public List<CompanyResponseDTO> findAll(){
        return companyBusiness.findAll();
    }

    public CompanyResponseDTO findById(String id){
        return companyBusiness.findById(id);
    }

    public CompanyResponseDTO post(CompanyRequestDTO data){
        return companyBusiness.post(data);
    }

    public CompanyResponseDTO put(CompanyRequestDTO data){
        return companyBusiness.put(data);
    }
}
