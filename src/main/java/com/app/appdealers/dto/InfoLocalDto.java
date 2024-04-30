package com.app.appdealers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InfoLocalDto {
    private Integer idLocal;
    private String nombreLocal;
    private String direccionLocal;
    private String telefonoLocal;
    private Integer idCoordenada;
    private Double latitud;
    private Double longitud;
}
