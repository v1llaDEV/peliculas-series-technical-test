package com.example.demo.mappers;


import com.example.demo.dtos.requests.PeliculaSerieRequest;
import com.example.demo.dtos.responses.PeliculaSerieResponse;
import com.example.demo.entities.PeliculaSerie;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PeliculaSerieMapper {

    List<PeliculaSerieRequest> listToRequest(List<PeliculaSerie> peliculaSerie);
    List<PeliculaSerieResponse> listToResponse(List<PeliculaSerie> peliculaSerie);
    List<PeliculaSerieResponse> listToResponsePage(Page<PeliculaSerie> peliculaSerieEntity);
    PeliculaSerieResponse toResponse(PeliculaSerie peliculaSerie);
    PeliculaSerie requestToEntity(PeliculaSerieRequest peliculaSerieRequest);
}
