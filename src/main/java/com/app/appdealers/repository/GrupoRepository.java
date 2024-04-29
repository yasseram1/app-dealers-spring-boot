package com.app.appdealers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.appdealers.entity.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer>{

    
}
