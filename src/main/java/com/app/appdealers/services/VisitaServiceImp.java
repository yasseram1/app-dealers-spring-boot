package com.app.appdealers.services;

import com.app.appdealers.dto.RegistroVisitaDto;
import com.app.appdealers.entity.Comercio;
import com.app.appdealers.entity.Usuario;
import com.app.appdealers.entity.Visita;
import com.app.appdealers.repository.ComercioRepository;
import com.app.appdealers.repository.GrupoRepository;
import com.app.appdealers.repository.UsuarioRepository;
import com.app.appdealers.repository.VisitaRepository;
import com.app.appdealers.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
public class VisitaServiceImp implements VisitaService {

    @Autowired
    private VisitaRepository visitaRepository;

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private GrupoRepository grupoRepository;


    @Override
    public ResponseEntity<?> registrarVisita(HttpServletRequest request, RegistroVisitaDto registroVisitaDto, Integer idComercio) {
        try {
            Usuario usuario = userUtil.getUsuarioFromRequest(request);
            Comercio comercio = comercioRepository.findById(idComercio).orElseThrow(() -> new NoSuchElementException("Comercio no encontrado"));
            Visita vista = registrarVisita(usuario, registroVisitaDto, comercio);
            visitaRepository.save(vista);

            return ResponseEntity.ok().body("Información de visita registrada");
        } catch (NoSuchElementException e) {
            System.out.println((e.getMessage()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println((e.getMessage()));
            return ResponseEntity.badRequest().body("Error al regsitrar la visita");
        }
    }

    private Visita registrarVisita(Usuario usuario, RegistroVisitaDto registroVisitaDto, Comercio comercio) {

        Visita visitExistence = visitaRepository.findVisitByCommerceIdAndGroupId(comercio.getId(), comercio.getGrupo().getId());

        if(visitExistence != null) {
            Visita visitUpdated = setVisitData(visitExistence, registroVisitaDto);
            visitUpdated.setFechaActualizacion(new Date());
            return visitUpdated;
        }

        Visita visita = setVisitData(new Visita(), registroVisitaDto);

        visita.setFechaCreacion(new Date());
        visita.setUsuario(usuario);
        visita.setGrupo(comercio.getGrupo());
        visita.setComercio(comercio);

        return visita;
    }

    private Visita setVisitData(Visita visita, RegistroVisitaDto registroVisitaDto) {
        visita.setEstado(registroVisitaDto.getEstado());
        visita.setRespuesta(registroVisitaDto.getRespuesta());
        visita.setDetalleVisita(registroVisitaDto.getDetalleRespuesta());
        visita.setFechaVisita(new Date());

        return visita;
    }
}
