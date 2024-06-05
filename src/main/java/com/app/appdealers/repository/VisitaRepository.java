package com.app.appdealers.repository;

import com.app.appdealers.entity.Visita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, Integer> {

    @Query("SELECT v FROM Visita v WHERE v.comercio.id = :idComercio AND v.grupo.id = :idGrupo")
    Optional<Visita> findVisitByCommerceIdAndGroupId(@Param("idComercio") Integer idComercio, @Param("idGrupo") Integer idGrupo);
}
