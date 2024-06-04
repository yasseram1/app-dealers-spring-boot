package com.app.appdealers.services;

import java.util.ArrayList;
import java.util.List;

import com.app.appdealers.repository.FaseRepository;
import com.app.appdealers.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

        // Comprobar si el dealer ya tiene un grupo asignado

        Grupo grupo = grupoRepository.obtenerGrupoSinDealer();

        if(grupo != null) {
            GrupoComerciosReponse gcr = new GrupoComerciosReponse();
            gcr.setCodigo(1);
            gcr.setMensaje("Lista de comercios del grupo con id: " + grupo.getId());
            
            gcr.setIdGrupo(grupo.getId());
            gcr.setDescripcionGrupo(grupo.getDescripcion());

            List<Comercio> locales = grupo.getLocales();
            
            List<InfoLocalDto> listaCoordenadas = new ArrayList<>();

            for(int i=0; i<locales.size(); i++) {

                Comercio comercio = locales.get(i);
                InfoLocalDto infoLocal = new InfoLocalDto();

                infoLocal.setIdLocal(comercio.getId());
                infoLocal.setNombreLocal(comercio.getRazonSocial());
                infoLocal.setDireccionLocal(comercio.getDireccion());
                infoLocal.setTelefonoLocal(comercio.getTelefono());
                infoLocal.setIdCoordenada(comercio.getCoordenadas().getId());
                infoLocal.setLatitud(locales.get(i).getCoordenadas().getLatitud());
                infoLocal.setLongitud(locales.get(i).getCoordenadas().getLongitud());

                listaCoordenadas.add(infoLocal);
            }

            grupo.setFase(faseRepository.findById(2).orElseThrow(null)); // TOMADO

            Usuario usuario = userUtil.getUsuarioFromRequest(request);
            grupo.setUsuario(usuario);


            grupoRepository.save(grupo);

            gcr.setCoordenadasListaComercios(listaCoordenadas);

            return new ResponseEntity<>(gcr, HttpStatus.OK);

        }

        GrupoComerciosReponse grcError = new GrupoComerciosReponse();
        grcError.setCodigo(2);
        grcError.setMensaje("No se encontro un grupo");

        return new ResponseEntity<>(grcError, HttpStatus.BAD_REQUEST);
    }


}
