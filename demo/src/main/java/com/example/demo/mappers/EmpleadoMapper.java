package com.example.demo.mappers;


import com.example.demo.dtos.requests.EmpleadoRequest;
import com.example.demo.dtos.responses.EmpleadoResponse;
import com.example.demo.entities.Empleado;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    List<EmpleadoRequest> listToRequest(List<Empleado> empleadoEntity);
    List<EmpleadoResponse> listToResponse(List<Empleado> empleadoEntity);
    List<EmpleadoResponse> listToResponsePage(Page<Empleado> empleadoEntity);
    EmpleadoResponse toResponse(Empleado empleadoEntity);
    Empleado requestToEntity(EmpleadoRequest empleadoEntity);
}
