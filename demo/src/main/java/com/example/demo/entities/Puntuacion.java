package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "PUNTUACIONES")
public class Puntuacion extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO", updatable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "ID_PELICULA_SERIE", updatable = false)
    private PeliculaSerie peliculaSerie;

    @Column(name = "PUNTUACION")
    private Double puntuacion;
}
