package com.app.appdealers.repository;

import com.app.appdealers.entity.Fase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaseRepository extends JpaRepository<Fase, Integer> {
}
