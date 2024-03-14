package com.example.demo.mappers;


import com.example.demo.dtos.requests.TipoProgramadorRequest;
import com.example.demo.dtos.responses.TipoProgramadorResponse;
import com.example.demo.entities.TipoProgramador;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TipoProgramadorMapper {

    List<TipoProgramadorRequest> listToRequest(List<TipoProgramador> rolEntity);
    List<TipoProgramadorResponse> listToResponse(List<TipoProgramador> rolEntity);
    TipoProgramadorResponse toResponse(TipoProgramador rolEntity);
    TipoProgramador requestToEntity(TipoProgramadorRequest rolEntity);
}
