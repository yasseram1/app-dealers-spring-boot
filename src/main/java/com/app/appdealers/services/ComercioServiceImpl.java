package com.app.appdealers.services;

import com.app.appdealers.dto.ComercioDto;
import com.app.appdealers.entity.Comercio;
import com.app.appdealers.entity.Coordenadas;
import com.app.appdealers.repository.ComercioRepository;
import com.app.appdealers.repository.CoordenadasRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ComercioServiceImpl implements ComercioService{

    @Autowired
    private ComercioRepository comercioRepository;

    @Autowired
    private CoordenadasRepository coordenadasRepository;

    @Override
    public ResponseEntity<?> crearComercio(ComercioDto comercioDto) {
        try {
            Comercio comercio = comercioDtoToComercio(comercioDto, new Comercio());
            comercio.setFechaCreacion(new Date());
            Coordenadas coordenadas = comercioDtoToCoordenadas(comercioDto, comercio, new Coordenadas());

            coordenadas.setFechaCreacion(new Date());
            comercio.setCoordenadas(coordenadas);

            coordenadasRepository.save(coordenadas);
            comercioRepository.save(comercio);

            return ResponseEntity.ok().body(comercio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Coordenadas comercioDtoToCoordenadas(ComercioDto comercioDto, Comercio comercio, Coordenadas coordenadas) {
        coordenadas.setComercio(comercio);
        coordenadas.setLatitud(comercioDto.getLatitud());
        coordenadas.setLongitud(comercioDto.getLongitud());
        return coordenadas;
    }

    @Override
    public ResponseEntity<?> editarComercio(Integer idComercio, ComercioDto comercioDto) {
        try {

            Optional<Comercio> comercioOptional = comercioRepository.findById(idComercio);
            Comercio comercio = comercioDtoToComercio(comercioDto, comercioOptional.get());
            comercio.setFechaActualizacion(new Date());
            Coordenadas coordenadas = comercioDtoToCoordenadas(comercioDto, comercio, comercio.getCoordenadas());
            coordenadas.setFechaActualizacion(new Date());

            coordenadasRepository.save(coordenadas);
            comercioRepository.save(comercio);
            return ResponseEntity.ok().body(comercio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> obtenerDataComercio(Integer idComercio) {
        try {
            Optional<Comercio> comercioOptional = comercioRepository.findById(idComercio);

            if(comercioOptional.isPresent()) {
                Comercio comercio = comercioOptional.get();

                ComercioDto comercioDto = new ComercioDto();

                comercioDto.setRazonSocial(comercio.getRazonSocial());
                comercioDto.setRuc(comercio.getRuc());
                comercioDto.setDireccion(comercio.getDireccion());
                comercioDto.setTelefono(comercio.getTelefono());
                comercioDto.setLongitud(comercio.getCoordenadas().getLongitud());
                comercioDto.setLatitud(comercio.getCoordenadas().getLatitud());

                return ResponseEntity.ok().body(comercioDto);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Transactional
    @Override
    public ResponseEntity<?> eliminarComercio(Integer idComercio) {
        try {
            Optional<Comercio> comercioOptional = comercioRepository.findById(idComercio);
            if(comercioOptional.isPresent()) {
                Coordenadas coordenadas = comercioOptional.get().getCoordenadas();
                coordenadasRepository.deleteById(coordenadas.getId());
                comercioRepository.deleteById(idComercio);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private Comercio comercioDtoToComercio(ComercioDto comercioDto, Comercio comercio) {
        comercio.setRazonSocial(comercioDto.getRazonSocial());
        comercio.setRuc(comercioDto.getRuc());
        comercio.setTelefono(comercioDto.getTelefono());
        comercio.setDireccion(comercioDto.getDireccion());

        return comercioRepository.save(comercio);
    }

}
