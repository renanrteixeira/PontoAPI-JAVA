package com.controle.ponto.persistence.role;

import com.controle.ponto.domain.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByName(String name);
}
