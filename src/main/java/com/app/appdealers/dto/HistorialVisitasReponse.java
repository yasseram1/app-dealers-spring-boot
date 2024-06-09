package com.app.appdealers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistorialVisitasReponse {
    private String ruc;
    private Date fecha;
    private String direccion;
}
