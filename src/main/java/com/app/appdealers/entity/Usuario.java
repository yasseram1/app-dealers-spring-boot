package com.app.appdealers.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Usuario implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_rol;

    @Column(name = "email")
    private String email;

    @Column(name = "nom_usuario")
    private String nombres;

    @Column(name = "ape_usuario")
    private String apellidos;

    @Column(name = "num_doc")
    private String dni;

    @Column(name = "tipo_doc")
    private String tipoDocumento;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_crereg")
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fec_actreg")
    private Date fechaActualizacion;
    
    @Column(name = "flg_activo")
    private boolean flg_activo;

    @ManyToOne
    @JoinColumn(name = "fk_rol")
    private Rol rol;

    @OneToMany(mappedBy = "usuario")
    private List<Grupo> grupo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNom_rol()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
