package com.app.appdealers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoordenadasDto {
    private Integer idCoordenada;
    private Double latitud;
    private Double longitud;
    private Integer idLocal;
}
