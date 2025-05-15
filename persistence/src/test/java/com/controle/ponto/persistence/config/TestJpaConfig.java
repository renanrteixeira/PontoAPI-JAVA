package com.controle.ponto.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.controle.ponto.domain")
@EnableJpaRepositories(basePackages = "com.controle.ponto.persistence")
public class TestJpaConfig {
}