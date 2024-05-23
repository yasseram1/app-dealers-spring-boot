package com.app.appdealers.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.apache.commons.math3.ml.clustering.Clusterable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comercios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comercio implements Clusterable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "ruc")
    private String ruc;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_crereg")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_actreg")
    private Date fechaActualizacion;

    @OneToOne(mappedBy = "comercio")
    private Coordenadas coordenadas;

    @OneToMany(mappedBy = "comercio", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Visita> visita;

    @ManyToOne
    @JoinColumn(name = "fk_grupo")
    private Grupo grupo;

    @Override
    public double[] getPoint() {
        if (coordenadas != null) {
            double latitud = this.coordenadas.getLatitud();
            double longitud = this.coordenadas.getLongitud();
            return new double[] { latitud, longitud };
        } else {
            throw new IllegalStateException("Coordenadas no disponibles para este local");
        }
    }

}
