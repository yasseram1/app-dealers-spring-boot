package com.app.appdealers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private String mensaje;
    private boolean error;
}
