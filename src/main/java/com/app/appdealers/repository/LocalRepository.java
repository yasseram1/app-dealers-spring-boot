package com.app.appdealers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.appdealers.entity.Comercio;

@Repository
public interface LocalRepository extends JpaRepository<Comercio, Integer>  {
    
    @Query("SELECT l FROM Local l INNER JOIN EstadoVisita esvi ON l.id = esvi.local.id WHERE esvi.estado.id = 3 AND l.grupo IS NULL")
    public List<Comercio> getAllLocalWithoutGroup();
}
