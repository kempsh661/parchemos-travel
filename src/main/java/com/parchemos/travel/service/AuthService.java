package com.parchemos.travel.service;

import com.parchemos.travel.dto.LoginRequestDTO;
import com.parchemos.travel.dto.LoginResponseDTO;
import com.parchemos.travel.dto.UsuarioDTO;
import com.parchemos.travel.dto.UsuarioRegistroDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO loginRequest);

    UsuarioDTO register(UsuarioRegistroDTO registroDTO);
}
