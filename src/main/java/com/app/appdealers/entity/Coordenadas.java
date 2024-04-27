package com.app.appdealers.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coordenadas")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Coordenadas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double latitud;

    private Double longitud;

    @OneToOne
    @JoinColumn(name = "fk_local")
    private Local local;

}
