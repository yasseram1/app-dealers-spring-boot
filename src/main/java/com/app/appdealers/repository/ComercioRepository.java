package com.app.appdealers.repository;

import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.appdealers.entity.Comercio;

@Repository
public interface ComercioRepository extends JpaRepository<Comercio, Integer>  {

    @Query("SELECT c FROM Comercio c LEFT JOIN c.visita v WHERE (v.id IS NULL OR v.fechaVisita < :oneMonthAgo) AND c.grupo IS NULL")
    public List<Comercio> findComerciosWithoutRecentVisita(Date oneMonthAgo);

    @Modifying
    @Transactional
    @Query("UPDATE Comercio c SET c.grupo = null WHERE c.grupo.fase.id = 1 OR c.grupo.fase.id = 3")
    public void setGrupoNullWhereFaseIsOneOrThree();
}
