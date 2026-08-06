package com.parchemos.travel.service;

import com.parchemos.travel.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    Page<Usuario> findAll(Pageable pageable);

    Usuario findById(Integer id);

    Usuario findByEmail(String email);

    Usuario save(Usuario usuario);

    Usuario update(Integer id, Usuario usuario);

    void deleteById(Integer id);
}
