package com.app.appdealers.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "grupo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Grupo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descripcion;

    @OneToMany(mappedBy = "grupo")
    private List<Local> locales;

    public Grupo(String descripcion, List<Local> locales) {
        this.descripcion = descripcion;
        this. locales = locales;
    }

    @ManyToOne
    @JoinColumn(name = "fk_dealer")
    private Usuario usuario;

}
