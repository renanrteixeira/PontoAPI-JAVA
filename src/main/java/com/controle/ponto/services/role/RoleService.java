package com.controle.ponto.services.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.role.Role;
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

        return role.orElse(null);
    }

    public Role postRole(RoleRequestDTO data){
        Optional<Role> role = Optional.ofNullable(repository.findByName(data.getName()));
        if (role.isPresent()){
            data.setId(null);
            return new Role(data);
        }

        Role newRole = new Role(data);
        repository.save(newRole);

        return newRole;
    }

    @Transactional
    public Role putRole(RoleRequestDTO data){
        Optional<Role> roleFound = repository.findById(data.getId());
        if(!roleFound.isPresent()){
            data.setId(null);
            return null;
        }

        Role newRole = roleFound.get();
        newRole.setName(data.getName());

        return newRole;
    }
}
