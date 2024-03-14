package com.example.demo.dtos.responses;

import com.example.demo.entities.Empleado;
import com.example.demo.entities.PeliculaSerie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PuntuacionResponse {

    private Long id;
    @JsonIgnoreProperties({"email", "password", "tipoProgramador", "fechaNacimiento", "rol", "fechaCreacion", "fechaActualizacion"})
    private Empleado empleado;
    @JsonIgnoreProperties({"anio", "director", "genero", "temporadas", "duracion", "empleadoProposicion", "empleadoImplantacion"
            ,"notaMedia",  "fechaCreacion", "fechaActualizacion"})
    private PeliculaSerie peliculaSerie;
    private Double puntuacion;
}
