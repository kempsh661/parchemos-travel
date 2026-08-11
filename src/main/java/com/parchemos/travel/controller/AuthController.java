package com.parchemos.travel.controller;

import com.parchemos.travel.dto.LoginRequestDTO;
import com.parchemos.travel.dto.LoginResponseDTO;
import com.parchemos.travel.dto.UsuarioDTO;
import com.parchemos.travel.dto.UsuarioRegistroDTO;
import com.parchemos.travel.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login y registro de usuarios")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Valida email y contraseña. No genera token JWT.")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crea un usuario con rol CLIENTE por defecto.")
    public UsuarioDTO register(@Valid @RequestBody UsuarioRegistroDTO registroDTO) {
        return authService.register(registroDTO);
    }
}
