package com.parchemos.travel.controller;

import com.parchemos.travel.dto.UsuarioDTO;
import com.parchemos.travel.dto.UsuarioRegistroDTO;
import com.parchemos.travel.model.Usuario;
import com.parchemos.travel.service.AuthService;
import com.parchemos.travel.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "CRUD de usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthService authService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, AuthService authService) {
        this.usuarioService = usuarioService;
        this.authService = authService;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Lista paginada de usuarios.")
    public Page<UsuarioDTO> getAllUsuarios(Pageable pageable) {
        return usuarioService.findAll(pageable).map(UsuarioDTO::fromEntity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID")
    public UsuarioDTO getUsuarioById(@PathVariable Integer id) {
        return UsuarioDTO.fromEntity(usuarioService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear usuario", description = "Equivale al registro; hashea la contraseña con BCrypt.")
    public UsuarioDTO createUsuario(@Valid @RequestBody UsuarioRegistroDTO registroDTO) {
        return authService.register(registroDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario")
    public UsuarioDTO updateUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioActualizado = usuarioService.update(id, usuarioDTO.toEntity());
        return UsuarioDTO.fromEntity(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario")
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteById(id);
    }
}
