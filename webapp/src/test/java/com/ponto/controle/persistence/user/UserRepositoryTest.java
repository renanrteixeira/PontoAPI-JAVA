package com.ponto.controle.persistence.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.user.User;
import com.controle.ponto.persistence.user.UserRepository;
import com.controle.ponto.webapp.PontoApplication;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = PontoApplication.class)
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findByUsername_Found() {
        String userName = "teste";
        UserRequestDTO data = new UserRequestDTO(null, "Teste", "teste@teste.com", userName, "1234", 'S', 'A');
        this.createUser(data);

        Optional<User> result = Optional.ofNullable(this.userRepository.findByUsername(userName));

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User successfully from DB")
    void findByUsername_NotFound() {
        String userName = "teste";
        Optional<User> result = Optional.ofNullable(this.userRepository.findByUsername(userName));

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserRequestDTO user){
        User newUser = new User(user);
        this.entityManager.persist(newUser);

        return newUser;
    }
}