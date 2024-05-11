package com.app.appdealers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String nombres;
    private String apellidos;
    private String telefono;
    private String email;
    private String password;
}
