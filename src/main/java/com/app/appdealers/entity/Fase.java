package com.app.appdealers.entity;

import com.app.appdealers.util.enums.FaseEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "fase")
public class Fase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_fase")
    @Enumerated(EnumType.STRING)
    private FaseEnum fase;

    @OneToMany(mappedBy = "fase")
    private List<Grupo> grupos;

}
