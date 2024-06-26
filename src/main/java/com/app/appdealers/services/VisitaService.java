package com.app.appdealers.services;

import com.app.appdealers.dto.RegistroVisitaDto;
import com.app.appdealers.util.enums.Respuesta;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface VisitaService {
    ResponseEntity<?> registrarVisita(HttpServletRequest request, RegistroVisitaDto registroVisitaDto, Integer idComercio);

    ResponseEntity<?> cargarDataVisita(HttpServletRequest request, Integer idComercio, Integer idGrupo);

    ResponseEntity<?> obtenerMetricasDealer(HttpServletRequest request, Integer idUsuario);

    ResponseEntity<?> obtenerHistorialVisitas(HttpServletRequest request, Respuesta respuesta);
}
