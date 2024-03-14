package com.example.demo.mappers;


import com.example.demo.dtos.requests.PuntuacionRequest;
import com.example.demo.dtos.responses.PuntuacionResponse;
import com.example.demo.entities.Puntuacion;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PuntuacionMapper {

    List<PuntuacionRequest> listToRequest(List<Puntuacion> puntuacion);
    List<PuntuacionResponse> listToResponse(List<Puntuacion> puntuacion);
    List<PuntuacionResponse> listToResponsePage(Page<Puntuacion> puntuacionEntity);
    PuntuacionResponse toResponse(Puntuacion puntuacion);
    Puntuacion requestToEntity(PuntuacionRequest puntuacionRequest);
}
