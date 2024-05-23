package com.app.appdealers.dto;

import com.app.appdealers.util.enums.Estado;
import com.app.appdealers.util.enums.Respuesta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroVisitaDto {
    private Estado estado;
    private Respuesta respuesta;
    private String detalleRespuesta;
}
