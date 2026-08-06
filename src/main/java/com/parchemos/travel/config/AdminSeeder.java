package com.parchemos.travel.config;

import com.parchemos.travel.model.RolUsuario;
import com.parchemos.travel.model.Usuario;
import com.parchemos.travel.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class AdminSeeder {

    private static final String ADMIN_EMAIL = "gerencia.parchemos@admin.co";
    private static final String ADMIN_PASSWORD = "Parchemos#2026";

    @Bean
    CommandLineRunner seedAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.existsByEmail(ADMIN_EMAIL)) {
                usuarioRepository.findByEmail(ADMIN_EMAIL).ifPresent(usuario -> {
                    if (usuario.getRol() != RolUsuario.ADMIN) {
                        usuario.setRol(RolUsuario.ADMIN);
                        usuarioRepository.save(usuario);
                    }
                });
                System.out.println("✅ Perfil ADMIN listo: " + ADMIN_EMAIL);
                return;
            }

            Usuario admin = new Usuario();
            admin.setNombre("Gerencia");
            admin.setApellido("Parchemos Travel");
            admin.setEmail(ADMIN_EMAIL);
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            admin.setRol(RolUsuario.ADMIN);
            admin.setFechaRegistro(LocalDateTime.now());
            usuarioRepository.save(admin);
            System.out.println("✅ Usuario ADMIN creado: " + ADMIN_EMAIL);
        };
    }
}
