package com.example.demo.services;

import com.example.demo.dtos.requests.RolRequest;
import com.example.demo.dtos.responses.RolResponse;

import java.util.List;

public interface RolService {

    List<RolResponse> getAll();
    RolResponse getRolById(Long id);
    RolResponse createRol(RolRequest rol);
    RolResponse updateRol(Long id, RolRequest rol);
    void deleteRol(Long id);
}
