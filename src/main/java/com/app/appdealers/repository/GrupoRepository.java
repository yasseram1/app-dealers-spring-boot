package com.app.appdealers.repository;

import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.appdealers.entity.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer>{

    @Query(value = "SELECT * FROM grupos g WHERE g.fk_dealer IS NULL AND g.id_fase = 1 LIMIT 1", nativeQuery = true)
    public Grupo obtenerGrupoSinDealer();

    @Modifying
    @Transactional
    @Query("DELETE FROM Grupo g WHERE g.fase.id = 1")
    public void deleteAllGroupsiWithIdFaseOne();

    @Query("SELECT g FROM Grupo g WHERE g.usuario.id = :idUser AND g.fase.id = :idPhase")
    public Grupo findGroupByUserIdAndPhaseId(@Param("idUser") Integer idUser, @Param("idPhase") Integer idPhase);

}
