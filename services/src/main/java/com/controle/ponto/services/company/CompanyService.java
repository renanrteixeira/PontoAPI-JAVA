package com.controle.ponto.services.company;

import com.controle.ponto.business.company.CompanyBusiness;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.dto.company.CompanyResponseDTO;
import com.controle.ponto.interfaces.ICrudService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CompanyService implements ICrudService<CompanyRequestDTO, CompanyResponseDTO> {

    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    private CompanyBusiness companyBusiness;

    public List<CompanyResponseDTO> findAll(){
        logger.debug("Delegando busca de todas as empresas");
        return companyBusiness.findAll();
    }

    public CompanyResponseDTO findById(String id){
        logger.debug("Delegando busca de empresa por ID {}", id);
        return companyBusiness.findById(id);
    }

    public CompanyResponseDTO post(CompanyRequestDTO data){
        logger.debug("Delegando criação de empresa {}", data.getName());
        return companyBusiness.post(data);
    }

    public CompanyResponseDTO put(CompanyRequestDTO data){
        logger.debug("Delegando atualização de empresa {}", data.getId());
        return companyBusiness.put(data);
    }
}
