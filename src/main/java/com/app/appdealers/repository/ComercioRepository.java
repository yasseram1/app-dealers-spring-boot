package com.app.appdealers.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.appdealers.entity.Comercio;

@Repository
public interface ComercioRepository extends JpaRepository<Comercio, Integer>  {

    @Query("SELECT c FROM Comercio c LEFT JOIN c.visita v WHERE v.id IS NULL OR v.fechaVisita < :oneMonthAgo")
    public List<Comercio> getAllComerciosWithoutGroup(Date oneMonthAgo);
}
