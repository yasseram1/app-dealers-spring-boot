package com.app.appdealers.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rol")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nom_rol;

    @Temporal(TemporalType.DATE)
    private LocalDateTime fec_crereg;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;

}
