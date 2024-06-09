package com.app.appdealers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CrearComercioDto {

    private String razonSocial;
    private String ruc;
    private String direccion;
    private String telefono;
    private Double latitud;
    private Double longitud;

}
