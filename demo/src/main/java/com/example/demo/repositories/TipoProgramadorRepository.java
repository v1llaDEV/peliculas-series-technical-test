package com.example.demo.repositories;

import com.example.demo.entities.TipoProgramador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoProgramadorRepository extends JpaRepository<TipoProgramador, Long> {

    boolean existsByNombre(String nombre);
}
