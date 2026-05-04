package com.controle.ponto.webapp.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class ExternalConfigEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String SYSTEM_NAME = "ponto";
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment,
                                       SpringApplication application) {

        String basePath = System.getProperty("app.configFolder");

        if (basePath != null) {

            List<String> files = List.of(
                    "application.properties",
                    "database.properties",
                    "app.key",
                    "app.pub"
            );

            for (String file : files) {
                load(environment, basePath, file);
            }
        }
    }

    private void load(ConfigurableEnvironment environment, String basePath, String relativePath) {

        Path configPath = Paths.get(basePath, StringUtils.joinWith("/", SYSTEM_NAME, relativePath));

        if (Files.exists(configPath)) {
            try (InputStream is = Files.newInputStream(configPath)) {

                Properties props = new Properties();
                props.load(is);

                environment.getPropertySources().addFirst(
                        new PropertiesPropertySource(relativePath, props)
                );

            } catch (IOException e) {
                throw new RuntimeException("Erro ao carregar " + relativePath, e);
            }
        }
    }
}
