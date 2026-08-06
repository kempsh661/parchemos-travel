package com.parchemos.travel.controller;

import com.parchemos.travel.dto.UsuarioDTO;
import com.parchemos.travel.dto.UsuarioRegistroDTO;
import com.parchemos.travel.model.Usuario;
import com.parchemos.travel.service.AuthService;
import com.parchemos.travel.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthService authService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, AuthService authService) {
        this.usuarioService = usuarioService;
        this.authService = authService;
    }

    @GetMapping
    public Page<UsuarioDTO> getAllUsuarios(Pageable pageable) {
        return usuarioService.findAll(pageable).map(UsuarioDTO::fromEntity);
    }

    @GetMapping("/{id}")
    public UsuarioDTO getUsuarioById(@PathVariable Integer id) {
        return UsuarioDTO.fromEntity(usuarioService.findById(id));
    }

    @PostMapping
    public UsuarioDTO createUsuario(@Valid @RequestBody UsuarioRegistroDTO registroDTO) {
        return authService.register(registroDTO);
    }

    @PutMapping("/{id}")
    public UsuarioDTO updateUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioActualizado = usuarioService.update(id, usuarioDTO.toEntity());
        return UsuarioDTO.fromEntity(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteById(id);
    }
}
