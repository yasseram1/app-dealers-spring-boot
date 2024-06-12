package com.app.appdealers.services;

import org.springframework.http.ResponseEntity;

import com.app.appdealers.util.response.GrupoComerciosReponse;

import jakarta.servlet.http.HttpServletRequest;

public interface IGrupoComerciosService {

    ResponseEntity<GrupoComerciosReponse> obtenerGrupoComercios(HttpServletRequest request);

    ResponseEntity<?> marcarGrupoTerminado(Integer idGrupo);
}
