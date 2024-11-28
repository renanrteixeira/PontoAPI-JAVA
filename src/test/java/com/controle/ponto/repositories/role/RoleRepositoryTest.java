package com.controle.ponto.repositories.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.role.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Role sucessfully from DB")
    void findByName_Found() {
        String name = "Dev";
        RoleRequestDTO data = new RoleRequestDTO(null, name);
        this.createRole(data);
        Optional<Role> role = Optional.ofNullable(roleRepository.findByName(name));

        assertThat(role.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Role sucessfully from DB")
    void findByName_NotFound() {
        String name = "Dev";
        Optional<Role> role = Optional.ofNullable(roleRepository.findByName(name));

        assertThat(role.isEmpty()).isTrue();
    }

    private Role createRole(RoleRequestDTO data){
        Role role = new Role(data);
        this.entityManager.persist(role);

        return role;
    }
}