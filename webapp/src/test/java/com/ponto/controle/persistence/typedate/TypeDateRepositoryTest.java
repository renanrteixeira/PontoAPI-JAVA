package com.controle.ponto.persistence.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.typedate.TypeDate;
import com.controle.ponto.webapp.PontoApplication;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = PontoApplication.class)
@Transactional
class TypeDateRepositoryTest {

    @Autowired
    TypeDateRepository typeDateRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get TypeDate sucessfully from DB")
    void findByName_Found() {
        String name = "teste";
        TypeDateRequestDTO data = new TypeDateRequestDTO(null, name, LocalTime.of(1,0), 'S');

        this.createTypeDate(data);

        Optional<TypeDate> result = Optional.ofNullable(typeDateRepository.findByName(name));

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get TypeDate sucessfully from DB")
    void findByName_NotFound() {
        String name = "teste";
        Optional<TypeDate> result = Optional.ofNullable(typeDateRepository.findByName(name));

        assertThat(result.isEmpty()).isTrue();
    }

    private TypeDate createTypeDate(TypeDateRequestDTO data){
        TypeDate typeDate = new TypeDate(data);
        this.entityManager.persist(typeDate);

        return typeDate;
    }
}