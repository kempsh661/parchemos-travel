package com.parchemos.travel.service;

import com.parchemos.travel.dto.LoginRequestDTO;
import com.parchemos.travel.dto.LoginResponseDTO;
import com.parchemos.travel.dto.UsuarioDTO;
import com.parchemos.travel.dto.UsuarioRegistroDTO;
import com.parchemos.travel.exception.UnauthorizedException;
import com.parchemos.travel.model.RolUsuario;
import com.parchemos.travel.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        System.out.println("Intentando login para: " + loginRequest.getEmail());
        Usuario usuario = usuarioService.findByEmail(loginRequest.getEmail());

        if (usuario.getPassword() == null || !passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new UnauthorizedException("Credenciales invalidas");
        }

        System.out.println("Login exitoso para: " + loginRequest.getEmail());
        return LoginResponseDTO.fromEntity(usuario);
    }

    @Override
    public UsuarioDTO register(UsuarioRegistroDTO registroDTO) {
        if (registroDTO.getRol() == null) {
            registroDTO.setRol(RolUsuario.CLIENTE);
        }
        Usuario usuario = registroDTO.toEntity();
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        Usuario usuarioGuardado = usuarioService.save(usuario);
        return UsuarioDTO.fromEntity(usuarioGuardado);
    }
}
