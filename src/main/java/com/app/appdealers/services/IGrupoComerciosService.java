package com.app.appdealers.services;

import org.springframework.http.ResponseEntity;

import com.app.appdealers.util.response.GrupoComerciosReponse;

public interface IGrupoComerciosService {

    ResponseEntity<GrupoComerciosReponse> obtenerGrupoComercios();
    
}
