package com.example.demo.dtos.requests;

import com.example.demo.entities.Empleado;
import com.example.demo.entities.PeliculaSerie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuntuacionRequest {

    private Empleado empleado;
    private PeliculaSerie peliculaSerie;
    private Double puntuacion;

}
