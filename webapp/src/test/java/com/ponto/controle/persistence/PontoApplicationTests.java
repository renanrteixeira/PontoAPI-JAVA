package com.ponto.controle.persistence;

import com.controle.ponto.webapp.PontoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = PontoApplication.class)
@ActiveProfiles("test")
class PontoApplicationTests {

	@Test
	void contextLoads() {
	}

}
