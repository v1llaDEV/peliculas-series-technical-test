package com.example.demo.repositories;

import com.example.demo.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    boolean existsByEmail(String email);
    Optional<Empleado> findByEmail(String email);

}
