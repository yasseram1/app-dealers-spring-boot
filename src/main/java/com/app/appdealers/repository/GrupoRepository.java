package com.app.appdealers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.appdealers.entity.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer>{

    @Query(value = "SELECT * FROM grupo g WHERE g.fk_dealer IS NULL LIMIT 1", nativeQuery = true)
    public Grupo obtenerGrupoSinDealer();
    
}
