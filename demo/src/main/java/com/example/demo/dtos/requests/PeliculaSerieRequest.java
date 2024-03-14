package com.example.demo.dtos.requests;

import com.example.demo.entities.Empleado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaSerieRequest {

    private String titulo;
    private Integer anio;
    private String director;
    private String genero;
    private Integer temporadas;
    private LocalTime duracion;
    private Empleado empleadoProposicion;
    private Empleado empleadoImplantacion;
    private Double notaMedia;

}
