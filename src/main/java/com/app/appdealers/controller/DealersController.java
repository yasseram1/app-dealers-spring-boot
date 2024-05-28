package com.app.appdealers.controller;

import com.app.appdealers.dto.RegistroVisitaDto;
import com.app.appdealers.services.VisitaService;
import org.springframework.web.bind.annotation.*;

import com.app.appdealers.services.IGrupoComerciosService;
import com.app.appdealers.util.response.GrupoComerciosReponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/v0/dealers")
@CrossOrigin(origins = "*")
public class DealersController {

    @Autowired
    private IGrupoComerciosService grupoComerciosService;

    @Autowired
    private VisitaService visitaService;

    @GetMapping("/obtenerGrupoComercios")
    public ResponseEntity<GrupoComerciosReponse> obtenerGrupoComercios(HttpServletRequest request) {
        return grupoComerciosService.obtenerGrupoComercios(request);
    }

    @PostMapping("/registrarVisita")
    public ResponseEntity<?> registrarVisita(HttpServletRequest request, @RequestBody RegistroVisitaDto registroVisitaDto, @RequestParam Integer idComercio) {
        System.out.println("ALSDJFLKAKLSDFA");
        return visitaService.registrarVisita(request, registroVisitaDto, idComercio);
    }
    

}
