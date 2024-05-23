package com.app.appdealers.services;

import com.app.appdealers.dto.RegistroVisitaDto;
import com.app.appdealers.entity.Usuario;
import com.app.appdealers.entity.Visita;
import com.app.appdealers.repository.UsuarioRepository;
import com.app.appdealers.repository.VisitaRepository;
import com.app.appdealers.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class VisitaServiceImp implements VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public ResponseEntity<?> registrarVisita(HttpServletRequest request, RegistroVisitaDto registroVisitaDto) {
        try {
            Visita nuevoRegistroVisita = new Visita();

            Usuario usuario = UserUtil.getUsuarioFromRequest(request);

            nuevoRegistroVisita.setUsuario(usuario);

            nuevoRegistroVisita.setEstado(registroVisitaDto.getEstado());
            nuevoRegistroVisita.setRespuesta(registroVisitaDto.getRespuesta());
            nuevoRegistroVisita.setDetalleVisita(registroVisitaDto.getDetalleRespuesta());

            nuevoRegistroVisita.setFechaVisita(new Date());
            nuevoRegistroVisita.setFechaCreacion(new Date());
            nuevoRegistroVisita.setFechaActualizacion(null);

            visitaRepository.save(nuevoRegistroVisita);

            return ResponseEntity.ok().body("Información de visita registrada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
