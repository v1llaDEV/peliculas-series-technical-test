package com.example.demo.mappers;


import com.example.demo.dtos.requests.RolRequest;
import com.example.demo.dtos.responses.RolResponse;
import com.example.demo.entities.Rol;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {

    List<RolRequest> listToRequest(List<Rol> rolEntity);
    List<RolResponse> listToResponse(List<Rol> rolEntity);
    RolResponse toResponse(Rol rolEntity);
    Rol requestToEntity(RolRequest rolEntity);
}
