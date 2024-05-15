package com.app.appdealers.services;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.app.appdealers.dto.AuthRequest;
import com.app.appdealers.dto.AuthResponse;
import com.app.appdealers.dto.RegisterRequest;
import com.app.appdealers.dto.RegisterResponse;

public interface UsuarioService {
    ResponseEntity<RegisterResponse> registrarUsuario(RegisterRequest user, BindingResult br);
    ResponseEntity<AuthResponse> login(AuthRequest authRequest);
}
