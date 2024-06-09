package com.app.appdealers.services;


import com.app.appdealers.dto.CrearComercioDto;
import com.app.appdealers.entity.Comercio;
import org.springframework.http.ResponseEntity;

public interface ComercioService {
    public ResponseEntity<?> crearComercio(CrearComercioDto comercio);

}
