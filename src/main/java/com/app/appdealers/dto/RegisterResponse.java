package com.app.appdealers.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterResponse {
    private String mensaje;
    private List<String> errores;
}
