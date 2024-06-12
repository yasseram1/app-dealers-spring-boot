package com.app.appdealers.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.app.appdealers.entity.Fase;
import com.app.appdealers.repository.FaseRepository;
import com.app.appdealers.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.appdealers.dto.InfoLocalDto;
import com.app.appdealers.entity.Grupo;
import com.app.appdealers.entity.Comercio;
import com.app.appdealers.entity.Usuario;
import com.app.appdealers.repository.GrupoRepository;
import com.app.appdealers.repository.UsuarioRepository;
import com.app.appdealers.services.jwt.JwtService;
import com.app.appdealers.util.response.GrupoComerciosReponse;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class GrupoComerciosServiceImpl implements IGrupoComerciosService {

    @Autowired 
    private UsuarioRepository usuarioRepository; // Provisional

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserUtil userUtil;

    @Autowired
    private FaseRepository faseRepository;

    @Override
    public ResponseEntity<GrupoComerciosReponse> obtenerGrupoComercios(HttpServletRequest request) {
        Usuario usuario = userUtil.getUsuarioFromRequest(request);
        Grupo grupoAsignado = grupoRepository.findGroupByUserIdAndPhaseId(usuario.getId(), 2);
        if(grupoAsignado != null) {
            GrupoComerciosReponse grupoComerciosReponse = getGrupoComerciosResponse(grupoAsignado);
            return new ResponseEntity<>(grupoComerciosReponse, HttpStatus.OK);
        }

        Grupo grupo = grupoRepository.obtenerGrupoSinDealer();
        if(grupo != null) {
            GrupoComerciosReponse grupoComerciosReponse = getGrupoComerciosResponse(grupo);
            grupo.setFase(faseRepository.findById(2).orElseThrow(null));
            grupo.setUsuario(usuario);
            grupoRepository.save(grupo);
            return new ResponseEntity<>(grupoComerciosReponse, HttpStatus.OK);
        }

        GrupoComerciosReponse grupoComerciosReponseError = new GrupoComerciosReponse();
        grupoComerciosReponseError.setCodigo(2);
        grupoComerciosReponseError.setMensaje("No se encontro un grupo");

        return new ResponseEntity<>(grupoComerciosReponseError, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> marcarGrupoTerminado(Integer idGrupo) {
        try {
            Optional<Grupo> grupoOptional = grupoRepository.findById(idGrupo);

            if(grupoOptional.isPresent()) {
                Grupo grupo = grupoOptional.get();
                Fase terminatedFase = faseRepository.findById(3).orElseThrow();
                grupo.setFase(terminatedFase);
                grupoRepository.save(grupo);
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private GrupoComerciosReponse getGrupoComerciosResponse(Grupo grupo) {
        GrupoComerciosReponse gcr = new GrupoComerciosReponse();
        gcr.setCodigo(1);
        gcr.setMensaje("Lista de comercios del grupo con id: " + grupo.getId());
        gcr.setIdGrupo(grupo.getId());
        gcr.setDescripcionGrupo(grupo.getDescripcion());
        gcr.setCoordenadasListaComercios(getInfoLocalDtos(grupo));
        return gcr;
    }

    private static List<InfoLocalDto> getInfoLocalDtos(Grupo grupo) {
        List<Comercio> locales = grupo.getLocales();
        List<InfoLocalDto> listCoordinates = new ArrayList<>();

        for (Comercio comercio : locales) {
            InfoLocalDto infoLocal = new InfoLocalDto();

            infoLocal.setIdLocal(comercio.getId());
            infoLocal.setNombreLocal(comercio.getRazonSocial());
            infoLocal.setDireccionLocal(comercio.getDireccion());
            infoLocal.setTelefonoLocal(comercio.getTelefono());
            infoLocal.setIdCoordenada(comercio.getCoordenadas().getId());
            infoLocal.setLatitud(comercio.getCoordenadas().getLatitud());
            infoLocal.setLongitud(comercio.getCoordenadas().getLongitud());

            listCoordinates.add(infoLocal);
        }

        return listCoordinates;
    }


}
