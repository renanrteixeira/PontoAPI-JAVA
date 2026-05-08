package com.controle.ponto.services.role;

import com.controle.ponto.business.role.RoleBusiness;
import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.dto.role.RoleResponseDTO;
import com.controle.ponto.interfaces.ICrudService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleService implements ICrudService<RoleRequestDTO, RoleResponseDTO> {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleBusiness roleBusiness;

    public List<RoleResponseDTO> findAll(){
        logger.debug("Delegando busca de todas as funções");
        return roleBusiness.findAll();
    }

    public RoleResponseDTO findById(String id){
        logger.debug("Delegando busca de função por ID {}", id);
        return roleBusiness.findById(id);
    }

    public RoleResponseDTO post(RoleRequestDTO data){
        logger.debug("Delegando criação de função {}", data.getName());
        return roleBusiness.post(data);
    }

    public RoleResponseDTO put(RoleRequestDTO data){
        logger.debug("Delegando atualização de função {}", data.getId());
        return roleBusiness.put(data);
    }
}
