package com.app.appdealers.util.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricasDealer {
        private Double promedioVisitasPorDia;
        private Double cantidadVisitasEstadoVisitado;
        private Double promedioVisitasNuevosTratosPorDia;
        private Double cantidadVisitasNuevosTratos;
}
