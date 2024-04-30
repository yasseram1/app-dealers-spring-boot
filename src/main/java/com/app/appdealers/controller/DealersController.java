package com.app.appdealers.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.appdealers.services.IGrupoComerciosService;
import com.app.appdealers.util.response.GrupoComerciosReponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v0/dealers")
public class DealersController {

    @Autowired
    private IGrupoComerciosService grupoComerciosService;

    @GetMapping("/obtenerGrupoComercios")
    public ResponseEntity<GrupoComerciosReponse> getMethodName() {
        return grupoComerciosService.obtenerGrupoComercios();
    }
    

}
