package com.example.demo.services;

import com.example.demo.dtos.requests.PuntuacionRequest;
import com.example.demo.dtos.responses.PuntuacionResponse;
import com.example.demo.exceptions.PageParametersInvalidException;

import java.util.List;

public interface PuntuacionService {

    List<PuntuacionResponse> getAllPuntuacionesPaginado(int pagina, int tamanio) throws PageParametersInvalidException;
    PuntuacionResponse getPuntuacionById(Long id);
    PuntuacionResponse getMaxPuntuacionByIdEmpleado(Long id);
    PuntuacionResponse createPuntuacion(PuntuacionRequest rol);
    PuntuacionResponse updatePuntuacion(PuntuacionRequest rol);
    void deletePuntuacion(Long id);
}
