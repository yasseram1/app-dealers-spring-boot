package com.app.appdealers.services;

import com.app.appdealers.dto.CrearComercioDto;
import com.app.appdealers.entity.Comercio;
import com.app.appdealers.entity.Coordenadas;
import com.app.appdealers.repository.ComercioRepository;
import com.app.appdealers.repository.CoordenadasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ComercioServiceImpl implements ComercioService{

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private CoordenadasRepository coordenadasRepository;

    @Override
    public ResponseEntity<?> crearComercio(CrearComercioDto comercioDto) {
        try {
            Comercio comercio = new Comercio();

            comercio.setRazonSocial(comercioDto.getRazonSocial());
            comercio.setRuc(comercioDto.getRuc());
            comercio.setTelefono(comercioDto.getTelefono());
            comercio.setDireccion(comercioDto.getDireccion());
            comercio.setFechaCreacion(new Date());
            comercioRepository.save(comercio);

            Coordenadas coordenadas = new Coordenadas();

            coordenadas.setComercio(comercio);
            coordenadas.setFechaCreacion(new Date());
            coordenadas.setLatitud(comercioDto.getLatitud());
            coordenadas.setLongitud(comercioDto.getLongitud());

            comercio.setCoordenadas(coordenadas);

            coordenadasRepository.save(coordenadas);
            comercioRepository.save(comercio);

            return ResponseEntity.ok().body(comercio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
