package com.example.demo.repositories;

import com.example.demo.entities.Empleado;
import com.example.demo.entities.PeliculaSerie;
import com.example.demo.entities.Puntuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PuntuacionRepository extends JpaRepository<Puntuacion, Long> {

    Optional<Puntuacion> findByEmpleadoAndPeliculaSerie(Empleado empleado, PeliculaSerie peliculaSerie);
    boolean existsByEmpleadoAndPeliculaSerie(Empleado empleado, PeliculaSerie peliculaSerie);

    @Query("SELECT MAX(p.puntuacion) as puntuacionMaxima, p.empleado.id as empleadoId, " +
            "p.peliculaSerie.id as peliculaId FROM Puntuacion p WHERE p.empleado.id = :idEmpleado GROUP BY p.empleado.id, p.peliculaSerie.id")
    List<Map> findMaxPuntuacionByEmpleado(@Param("idEmpleado") Long idEmpleado);
}
