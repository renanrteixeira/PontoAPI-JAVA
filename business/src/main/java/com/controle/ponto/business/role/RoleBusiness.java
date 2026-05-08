package com.controle.ponto.business.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.dto.role.RoleResponseDTO;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.NotFoundCustomException;
import com.controle.ponto.domain.mappers.role.RoleMapper;
import com.controle.ponto.domain.entity.role.Role;
import com.controle.ponto.persistence.role.RoleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RoleBusiness {

    private static final Logger logger = LoggerFactory.getLogger(RoleBusiness.class);

    @Autowired
    private RoleRepository repository;

    public List<RoleResponseDTO> findAll(){
        logger.info("Buscando todas as funções");
        var roles = repository.findAll();

        List<RoleResponseDTO> roleList = new ArrayList<>();

        for (Role role : roles){
            RoleResponseDTO newRoleDTO = RoleMapper.INSTANCE.toResponseDTO(role);
            roleList.add(newRoleDTO);
        }

        logger.info("Encontradas {} funções", roleList.size());
        return roleList;
    }

    public RoleResponseDTO findById(String id){
        logger.info("Buscando função por ID {}", id);
        Optional<Role> role = getRoleFindById(id);
        Role rolefound = role.get();
        logger.info("Função encontrada: {}", id);

        return RoleMapper.INSTANCE.toResponseDTO(rolefound);
    }

    private Optional<Role> getRoleFindById(String id) {
        Optional<Role> role = repository.findById(id);
        if (!role.isPresent()){
            logger.warn("Função não encontrada: {}", id);
            throw new NotFoundCustomException("Função não encontrada!");
        }
        return role;
    }

    public RoleResponseDTO post(RoleRequestDTO data){
        logger.info("Criando nova função {}", data.getName());
        Optional<Role> role = Optional.ofNullable(repository.findByName(data.getName()));
        if (role.isPresent()){
            logger.warn("Tentativa de criar função duplicada: {}", data.getName());
            throw new BadRequestCustomException("Função já cadastrada.");
        }

        Role newRole = new Role(data);
        repository.save(newRole);
        logger.info("Função criada com sucesso");

        return RoleMapper.INSTANCE.toResponseDTO(newRole);
    }

    @Transactional
    public RoleResponseDTO put(RoleRequestDTO data){
        logger.info("Atualizando função {}", data.getId());
        Optional<Role> roleFound = getRoleFindById(data.getId());

        Role newRole = roleFound.get();
        newRole.setName(data.getName());
        logger.info("Função atualizada com sucesso");

        return RoleMapper.INSTANCE.toResponseDTO(newRole);
    }
}
