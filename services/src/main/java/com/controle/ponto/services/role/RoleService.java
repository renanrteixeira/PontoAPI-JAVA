package com.controle.ponto.services.role;

import com.controle.ponto.business.role.RoleBusiness;
import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.dto.role.RoleResponseDTO;
import com.controle.ponto.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IService<RoleRequestDTO, RoleResponseDTO> {

    @Autowired
    private RoleBusiness roleBusiness;

    public List<RoleResponseDTO> findAll(){
       return roleBusiness.findAll();
    }

    public RoleResponseDTO findById(String id){
        return roleBusiness.findById(id);
    }

    public RoleResponseDTO post(RoleRequestDTO data){
        return roleBusiness.post(data);
    }

    public RoleResponseDTO put(RoleRequestDTO data){
        return roleBusiness.put(data);
    }
}
