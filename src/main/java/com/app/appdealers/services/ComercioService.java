package com.app.appdealers.services;


import com.app.appdealers.dto.ComercioDto;
import org.springframework.http.ResponseEntity;

public interface ComercioService {
    public ResponseEntity<?> crearComercio(ComercioDto comercio);

    ResponseEntity<?> editarComercio(Integer idComercio, ComercioDto comercioDto);

    ResponseEntity<?> obtenerDataComercio(Integer idComercio);

    ResponseEntity<?> eliminarComercio(Integer idComercio);
}
