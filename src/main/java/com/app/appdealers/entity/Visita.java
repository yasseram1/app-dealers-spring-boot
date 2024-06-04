package com.app.appdealers.entity;

import java.util.Date;

import com.app.appdealers.util.enums.Estado;
import com.app.appdealers.util.enums.Respuesta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "visitas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "respuesta")
    private Respuesta respuesta;

    @Column(name = "detalle_visita")
    private String detalleVisita;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_visita")
    private Date fechaVisita;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_crereg")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_actreg")
    private Date fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_local")
    private Comercio comercio;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

}
