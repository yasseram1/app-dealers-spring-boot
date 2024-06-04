package com.app.appdealers.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grupos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Grupo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_crereg")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_actreg")
    private Date fechaActualizacion;

    @OneToMany(mappedBy = "grupo")
    private List<Comercio> locales;

    @ManyToOne
    @JoinColumn(name = "id_fase")
    private Fase fase;

    @OneToMany(mappedBy = "grupo")
    private List<Visita> visitas;

    public Grupo(String descripcion, List<Comercio> locales) {
        this.descripcion = descripcion;
        this. locales = locales;
    }

    @ManyToOne
    @JoinColumn(name = "fk_dealer")
    private Usuario usuario;

}
