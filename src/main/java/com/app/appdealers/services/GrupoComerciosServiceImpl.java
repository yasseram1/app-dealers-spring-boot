package com.app.appdealers.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.appdealers.dto.CoordenadasDto;
import com.app.appdealers.entity.Grupo;
import com.app.appdealers.entity.Local;
import com.app.appdealers.repository.GrupoRepository;
import com.app.appdealers.util.response.GrupoComerciosReponse;

@Service
public class GrupoComerciosServiceImpl implements IGrupoComerciosService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Override
    public ResponseEntity<GrupoComerciosReponse> obtenerGrupoComercios() {

        // Falta agregar asignacion de grupo a un usuario en especifico
        
        Grupo grupo = grupoRepository.obtenerGrupoSinDealer();
        
        if(grupo != null) {
            GrupoComerciosReponse gcr = new GrupoComerciosReponse();
            gcr.setCodigo(1);
            gcr.setMensaje("Lista de comercios del grupo con id: " + grupo.getId());
            
            gcr.setIdGrupo(grupo.getId());
            gcr.setDescripcionGrupo(grupo.getDescripcion());

            List<Local> locales = grupo.getLocales();
            
            List<CoordenadasDto> listaCoordenadas = new ArrayList<>();

            for(int i=0; i<locales.size(); i++) {
                
                Local local = locales.get(i);
                CoordenadasDto coordenadas = new CoordenadasDto();

                coordenadas.setIdCoordenada(local.getCoordenadas().getId());
                coordenadas.setIdLocal(local.getId());
                coordenadas.setLatitud(locales.get(i).getCoordenadas().getLatitud());
                coordenadas.setLongitud(locales.get(i).getCoordenadas().getLongitud());

                listaCoordenadas.add(coordenadas);
            }

            gcr.setCoordenadasListaComercios(listaCoordenadas);

            return new ResponseEntity<>(gcr, HttpStatus.OK);

        }

        GrupoComerciosReponse grcError = new GrupoComerciosReponse();
        grcError.setCodigo(2);
        grcError.setMensaje("No se encontro un grupo");

        return new ResponseEntity<>(grcError, HttpStatus.BAD_REQUEST);
    }
    


}
