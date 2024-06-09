package com.app.appdealers.repository;

import com.app.appdealers.entity.Coordenadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadasRepository extends JpaRepository<Coordenadas, Integer> {

    @Query("SELECT MAX(c.id) FROM Coordenadas c")
    public Integer getLastId();

}
