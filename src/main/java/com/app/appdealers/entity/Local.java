package com.app.appdealers.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locales")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Local {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String direccion;

    private String telefono;

    @OneToMany(mappedBy = "local", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<EstadoVisita> estadoVisita;

    @ManyToOne
    @JoinColumn(name = "fk_grupo")
    private Grupo grupo;

}
