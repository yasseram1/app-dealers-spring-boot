package com.app.appdealers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.appdealers.entity.Comercio;

@Repository
public interface LocalRepository extends JpaRepository<Comercio, Integer>  {
    
    @Query("SELECT c FROM Comercio c")
    public List<Comercio> getAllComerciosWithoutGroup();
}
