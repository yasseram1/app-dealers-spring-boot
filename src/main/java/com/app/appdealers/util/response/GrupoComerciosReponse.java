package com.app.appdealers.util.response;

import java.util.List;

import com.app.appdealers.dto.CoordenadasDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GrupoComerciosReponse {
    
    private Integer codigo;
    private String mensaje;
    private Integer idGrupo;
    private String descripcionGrupo;
    private List<CoordenadasDto> coordenadasListaComercios;
    
}
