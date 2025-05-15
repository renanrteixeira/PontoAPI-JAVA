package com.controle.ponto.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableAutoConfiguration
@EnableWebSecurity
@SpringBootApplication(scanBasePackages = {"com.controle.ponto"})
@EnableJpaRepositories(basePackages = "com.controle.ponto.persistence")
@EntityScan(basePackages = "com.controle.ponto.domain")
public class PontoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PontoApplication.class, args);
	}

}
