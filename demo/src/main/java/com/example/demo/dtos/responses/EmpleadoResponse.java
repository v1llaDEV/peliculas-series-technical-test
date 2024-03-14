package com.example.demo.dtos.responses;

import com.example.demo.entities.Rol;
import com.example.demo.entities.TipoProgramador;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpleadoResponse {

    private Long id;
    private String nombre;
    private String email;
    @JsonIgnoreProperties({"fechaCreacion", "fechaActualizacion"})
    private TipoProgramador tipoProgramador;
    private LocalDate fechaNacimiento;
    @JsonIgnoreProperties({"fechaCreacion", "fechaActualizacion"})
    private Rol rol;
}
