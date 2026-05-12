package com.controle.ponto.persistence.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.entity.enums.AdminStatus;
import com.controle.ponto.domain.entity.enums.UserStatus;
import com.controle.ponto.domain.mappers.user.UserMapper;
import com.controle.ponto.domain.entity.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@ContextConfiguration(classes = com.controle.ponto.persistence.config.TestJpaConfig.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findByUsername_Found() {
        String userName = "teste";
        UserRequestDTO data = new UserRequestDTO(null, "Teste", "teste@teste.com", userName, "1234", AdminStatus.YES, UserStatus.ACTIVE);
        this.createUser(data);

        Optional<User> result = this.userRepository.findByUsername(userName);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User successfully from DB")
    void findByUsername_NotFound() {
        String userName = "teste";
        Optional<User> result = this.userRepository.findByUsername(userName);

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserRequestDTO user){
        User newUser = UserMapper.INSTANTE.toResquestEntity(user);
        this.entityManager.persist(newUser);

        return newUser;
    }
}