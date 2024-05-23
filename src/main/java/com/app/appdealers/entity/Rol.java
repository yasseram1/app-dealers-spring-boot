package com.app.appdealers.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom_rol")
    private String nombre;

    @Temporal(TemporalType.DATE)
    private Date fec_crereg;

    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;

}
