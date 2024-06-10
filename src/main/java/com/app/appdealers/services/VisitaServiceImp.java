package com.app.appdealers.services;

import com.app.appdealers.dto.HistorialVisitasReponse;
import com.app.appdealers.dto.RegistroVisitaDto;
import com.app.appdealers.entity.Comercio;
import com.app.appdealers.entity.Usuario;
import com.app.appdealers.entity.Visita;
import com.app.appdealers.repository.ComercioRepository;
import com.app.appdealers.repository.GrupoRepository;
import com.app.appdealers.repository.UsuarioRepository;
import com.app.appdealers.repository.VisitaRepository;
import com.app.appdealers.util.UserUtil;
import com.app.appdealers.util.enums.Respuesta;
import com.app.appdealers.util.response.MetricasDealer;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public ResponseEntity<?> cargarDataVisita(HttpServletRequest request, Integer idComercio, Integer idGrupo) {

        Optional<Visita> visit = visitaRepository.findVisitByCommerceIdAndGroupId(idComercio, idGrupo);

        RegistroVisitaDto registroVisitaDto  = new RegistroVisitaDto();

        if(visit.isPresent() && visit.get().getUsuario() == userUtil.getUsuarioFromRequest(request)) {
            Visita visitExistent = visit.get();
            registroVisitaDto.setEstado(visitExistent.getEstado());
            registroVisitaDto.setRespuesta(visitExistent.getRespuesta());
            registroVisitaDto.setDetalleRespuesta(visitExistent.getDetalleVisita());
        }

        return ResponseEntity.ok().body(registroVisitaDto);
    }

    @Override
    public ResponseEntity<?> obtenerMetricasDealer(HttpServletRequest request, Integer idUsuario) {
        try {
            MetricasDealer metricasDealer = new MetricasDealer();
            metricasDealer.setPromedioVisitasPorDia(visitaRepository.obtenerPromedioComerciosVisitadosPorDia(idUsuario));
            metricasDealer.setCantidadVisitasEstadoVisitado(visitaRepository.obtenerCantidadComerciosVisitados(idUsuario));
            metricasDealer.setPromedioVisitasNuevosTratosPorDia(visitaRepository.obtenerPromedioComercioAfiliados(idUsuario));
            metricasDealer.setCantidadVisitasNuevosTratos(visitaRepository.obtenerCantidadComerciosAfiliados(idUsuario));

            return ResponseEntity.ok().body(metricasDealer);
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<?> obtenerHistorialVisitas(HttpServletRequest request, Respuesta respuesta) {
        List<Visita> historialVisitas;
        Integer idUsuario = userUtil.getUsuarioFromRequest(request).getId();
        if(respuesta == null) {
            historialVisitas =  visitaRepository.obtenerHistorialVisitasParaDealer(idUsuario);
        } else {
            historialVisitas =  visitaRepository.obtenerHistorialVisitasParaDealerPorRespuesta(idUsuario, respuesta);;
        }

        List<HistorialVisitasReponse> historialVisita = new ArrayList<>();

        for(Visita visita : historialVisitas) {
            HistorialVisitasReponse historialVisitasReponse = new HistorialVisitasReponse();
            historialVisitasReponse.setRazonSocial(visita.getComercio().getRazonSocial());
            historialVisitasReponse.setFecha(visita.getFechaVisita());
            historialVisitasReponse.setDireccion(visita.getComercio().getDireccion());
            historialVisita.add(historialVisitasReponse);
        }

        return ResponseEntity.ok().body(historialVisita);

    }

    private Visita registrarVisita(Usuario usuario, RegistroVisitaDto registroVisitaDto, Comercio comercio) {
        Optional<Visita> visitExistence = visitaRepository.findVisitByCommerceIdAndGroupId(comercio.getId(), comercio.getGrupo().getId());

        if(visitExistence.isPresent()) {
            Visita visitUpdated = setVisitData(visitExistence.get(), registroVisitaDto);
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
