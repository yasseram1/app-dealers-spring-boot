package com.app.appdealers.controller;

import com.app.appdealers.dto.ComercioDto;
import com.app.appdealers.dto.RegistroVisitaDto;
import com.app.appdealers.services.ComercioService;
import com.app.appdealers.services.UsuarioService;
import com.app.appdealers.services.VisitaService;
import com.app.appdealers.util.enums.Respuesta;
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

    @Autowired
    private ComercioService comercioService;

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

    @GetMapping("/obtenerMetricasDealer")
    public ResponseEntity<?> obtenerMetricasDealer(HttpServletRequest request, @RequestParam Integer idUsuario) {
        return visitaService.obtenerMetricasDealer(request, idUsuario);
    }

    @GetMapping("/obtenerHistorialVisitas")
    public ResponseEntity<?> obtenerHistorialVisitas(HttpServletRequest request, @RequestParam(required = false) Respuesta respuesta) {
        return visitaService.obtenerHistorialVisitas(request, respuesta);
    }

    @PostMapping("/crearComercio")
    public ResponseEntity<?> crearComercio(@RequestBody ComercioDto comercio) {
        return comercioService.crearComercio(comercio);
    }

    @PutMapping("/editarComercio")
    public ResponseEntity<?> editarComercio(@RequestParam Integer idComercio, @RequestBody ComercioDto comercioDto) {
        return comercioService.editarComercio(idComercio, comercioDto);
    }

    @GetMapping("/obtenerDataComercio")
    public ResponseEntity<?> obtenerDataComercio(@RequestParam Integer idComercio) {
        return comercioService.obtenerDataComercio(idComercio);
    }

    @DeleteMapping("/eliminarComercio")
    public ResponseEntity<?> eliminarComercio(@RequestParam Integer idComercio) {
        return comercioService.eliminarComercio(idComercio);
    }

}
