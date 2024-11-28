package com.controle.ponto.repositories.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
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