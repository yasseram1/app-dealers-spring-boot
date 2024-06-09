package com.app.appdealers.controller;

import com.app.appdealers.dto.RegistroVisitaDto;
import com.app.appdealers.services.UsuarioService;
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

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/obtenerGrupoComercios")
    public ResponseEntity<GrupoComerciosReponse> obtenerGrupoComercios(HttpServletRequest request) {
        return grupoComerciosService.obtenerGrupoComercios(request);
    }

    @PostMapping("/registrarVisita")
    public ResponseEntity<?> registrarVisita(HttpServletRequest request, @RequestBody RegistroVisitaDto registroVisitaDto, @RequestParam Integer idComercio) {
        return visitaService.registrarVisita(request, registroVisitaDto, idComercio);
    }

    @GetMapping("/cargarDataVisita")
    public ResponseEntity<?> cargarDataVisita(HttpServletRequest request, @RequestParam Integer idComercio, @RequestParam Integer idGrupo) {
        return visitaService.cargarDataVisita(request, idComercio, idGrupo);
    }

    @GetMapping("/obtenerDealers")
    public ResponseEntity<?> obtenerDealers(HttpServletRequest request) {
        return usuarioService.getAllDealers();
    }

}
