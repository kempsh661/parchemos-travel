package com.parchemos.travel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI parchemosTravelOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Parchemos Travel API")
                        .description("""
                                API REST de Parchemos Travel para gestionar destinos, paquetes turísticos,
                                usuarios, presupuestos, reservas, pagos y reseñas.

                                Autenticación: login/registro con email y contraseña (BCrypt).
                                Actualmente no se emite JWT ni se protegen los endpoints con Spring Security.
                                """)
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Parchemos Travel")
                                .email("gerencia.parchemos@admin.co"))
                        .license(new License()
                                .name("Uso interno del proyecto")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Desarrollo local"),
                        new Server()
                                .url("/")
                                .description("Servidor actual")
                ));
    }
}
