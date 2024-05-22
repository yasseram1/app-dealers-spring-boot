package com.app.appdealers.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado_visita")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstadoVisita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date fechaUltimaVisita;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_crereg")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_actreg")
    private Date fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "fk_local")
    private Comercio comercio;

    @ManyToOne
    @JoinColumn(name = "fk_estado")
    private Estado estado;

}
