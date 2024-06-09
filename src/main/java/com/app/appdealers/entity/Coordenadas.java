package com.app.appdealers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "coordenadas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coordenadas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lat")
    private Double latitud;

    @Column(name = "long")
    private Double longitud;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_crereg")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_actreg")
    private Date fechaActualizacion;

    @OneToOne
    @JoinColumn(name = "fk_local")
    @JsonIgnore
    private Comercio comercio;

}
