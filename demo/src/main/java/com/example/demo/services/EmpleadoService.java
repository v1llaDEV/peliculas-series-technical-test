package com.example.demo.services;

import com.example.demo.dtos.requests.EmpleadoRequest;
import com.example.demo.dtos.responses.EmpleadoResponse;
import com.example.demo.exceptions.PageParametersInvalidException;

import java.util.List;

public interface EmpleadoService {

    List<EmpleadoResponse> getAllEmpleadosPaginado(int pagina, int tamanio) throws PageParametersInvalidException;
    EmpleadoResponse getEmpleadoById(Long id);
    EmpleadoResponse createEmpleado(EmpleadoRequest Empleado);
    EmpleadoResponse updateEmpleado(Long id, EmpleadoRequest Empleado);
    void deleteEmpleado(Long id);
}
