package com.app.appdealers.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.app.appdealers.dto.AuthRequest;
import com.app.appdealers.dto.RegisterRequest;

@Service
public interface UserService {
    ResponseEntity<?> createUser(RegisterRequest user, BindingResult br);
    ResponseEntity<?> login(AuthRequest authRequest);
}
