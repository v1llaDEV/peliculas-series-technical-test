package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "PELICULAS_SERIES")
public class PeliculaSerie extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String genero;

    private Integer temporadas;

    private LocalTime duracion;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO_PROPOSICION")
    private Empleado empleadoProposicion;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO_IMPLANTACION")
    private Empleado empleadoImplantacion;

    @Column(precision = 3, scale = 1)
    private Double notaMedia;
}
