package com.app.appdealers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.appdealers.entity.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Integer>  {
    
    @Query("SELECT l FROM Local l")
    public List<Local> getAllLocalWithoutGroup();
}
