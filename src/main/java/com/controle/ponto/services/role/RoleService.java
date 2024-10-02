package com.controle.ponto.services.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.role.Role;
import com.controle.ponto.exceptions.BadRequestCustomException;
import com.controle.ponto.exceptions.NotFoundCustomException;
import com.controle.ponto.repositories.role.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository repository;

    public List<Role> getAllRoles(){
        return repository.findAll();
    }

    public Role getById(String id){
        Optional<Role> role = repository.findById(id);
        if (!role.isPresent()){
            throw new NotFoundCustomException("Função não encontrada!");
        }
        Role rolefound = role.get();

        return rolefound;
    }

    public Role postRole(RoleRequestDTO data){
        Optional<Role> role = Optional.ofNullable(repository.findByName(data.getName()));
        if (role.isPresent()){
            throw new BadRequestCustomException("Função já cadastrada.");
        }

        Role newRole = new Role(data);
        repository.save(newRole);

        return newRole;
    }

    @Transactional
    public Role putRole(RoleRequestDTO data){
        Optional<Role> roleFound = repository.findById(data.getId());
        if(!roleFound.isPresent()){
            throw new NotFoundCustomException("Função não encontrada!");
        }

        Role newRole = roleFound.get();
        newRole.setName(data.getName());

        return newRole;
    }
}
