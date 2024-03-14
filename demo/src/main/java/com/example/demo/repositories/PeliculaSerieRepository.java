package com.example.demo.repositories;

import com.example.demo.entities.PeliculaSerie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeliculaSerieRepository extends JpaRepository<PeliculaSerie, Long> {

    boolean existsByTitulo(String titulo);
    List<PeliculaSerie> findByTemporadasIsNull();
    List<PeliculaSerie> findByDuracionIsNull();
}
