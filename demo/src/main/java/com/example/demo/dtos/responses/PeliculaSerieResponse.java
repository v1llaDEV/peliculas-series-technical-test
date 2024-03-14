package com.example.demo.dtos.responses;

import com.example.demo.entities.Empleado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PeliculaSerieResponse {

    private Long id;
    private String titulo;
    private Integer anio;
    private String director;
    private String genero;
    private Integer temporadas;
    private LocalTime duracion;
    @JsonIgnoreProperties({"email", "password", "tipoProgramador", "fechaNacimiento", "rol", "fechaCreacion", "fechaActualizacion"})
    private Empleado empleadoProposicion;
    @JsonIgnoreProperties({"email", "password", "tipoProgramador", "fechaNacimiento", "rol", "fechaCreacion", "fechaActualizacion"})
    private Empleado empleadoImplantacion;
    private Double notaMedia;

}
