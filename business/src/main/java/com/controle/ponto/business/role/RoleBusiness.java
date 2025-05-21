package com.controle.ponto.business.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.dto.role.RoleResponseDTO;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.NotFoundCustomException;
import com.controle.ponto.domain.mappers.role.RoleMapper;
import com.controle.ponto.domain.entity.role.Role;
import com.controle.ponto.persistence.role.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RoleBusiness {

    @Autowired
    private RoleRepository repository;

    public List<RoleResponseDTO> findAll(){
        var roles = repository.findAll();

        List<RoleResponseDTO> roleList = new ArrayList<>();

        for (Role role : roles){
            RoleResponseDTO newRoleDTO = RoleMapper.INSTANCE.toResponseDTO(role);
            roleList.add(newRoleDTO);
        }

        return roleList;
    }

    public RoleResponseDTO findById(String id){
        Optional<Role> role = getRoleFindById(id);
        Role rolefound = role.get();

        return RoleMapper.INSTANCE.toResponseDTO(rolefound);
    }

    private Optional<Role> getRoleFindById(String id) {
        Optional<Role> role = repository.findById(id);
        if (!role.isPresent()){
            throw new NotFoundCustomException("Função não encontrada!");
        }
        return role;
    }

    public RoleResponseDTO post(RoleRequestDTO data){
        Optional<Role> role = Optional.ofNullable(repository.findByName(data.getName()));
        if (role.isPresent()){
            throw new BadRequestCustomException("Função já cadastrada.");
        }

        Role newRole = new Role(data);
        repository.save(newRole);

        return RoleMapper.INSTANCE.toResponseDTO(newRole);
    }

    @Transactional
    public RoleResponseDTO put(RoleRequestDTO data){
        Optional<Role> roleFound = getRoleFindById(data.getId());

        Role newRole = roleFound.get();
        newRole.setName(data.getName());

        return RoleMapper.INSTANCE.toResponseDTO(newRole);
    }

}
