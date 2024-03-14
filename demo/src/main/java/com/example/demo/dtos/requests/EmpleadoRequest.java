package com.example.demo.dtos.requests;

import com.example.demo.entities.Rol;
import com.example.demo.entities.TipoProgramador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoRequest {

    private String nombre;
    private String email;
    private String password;
    private TipoProgramador tipoProgramador;
    private LocalDate fechaNacimiento;
    private Rol rol;
}
