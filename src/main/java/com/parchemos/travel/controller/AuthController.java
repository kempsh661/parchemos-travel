package com.parchemos.travel.controller;

import com.parchemos.travel.dto.LoginRequestDTO;
import com.parchemos.travel.dto.LoginResponseDTO;
import com.parchemos.travel.dto.UsuarioDTO;
import com.parchemos.travel.dto.UsuarioRegistroDTO;
import com.parchemos.travel.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UsuarioDTO register(@Valid @RequestBody UsuarioRegistroDTO registroDTO) {
        return authService.register(registroDTO);
    }
}
