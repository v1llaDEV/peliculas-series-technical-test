package com.example.demo.services.impl;

import com.example.demo.dtos.requests.RolRequest;
import com.example.demo.dtos.responses.RolResponse;
import com.example.demo.entities.Rol;
import com.example.demo.mappers.RolMapper;
import com.example.demo.repositories.RolRepository;
import com.example.demo.services.RolService;
import com.example.demo.validations.RolValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final RolValidations rolValidations;
    private final RolMapper rolMapper;

    @Cacheable("roles")
    @Override
    public List<RolResponse> getAll(){
        List<Rol> rolesResult = rolRepository.findAll();
        log.info("Consultando todos los roles {}", rolesResult);
        return rolMapper.listToResponse(rolesResult);
    }

    @Cacheable("roles")
    @Override
    public RolResponse getRolById(Long id) {
        Rol rolEntity = rolValidations.validarCampoId(id);
        log.info("Consultando roles por id {}", rolEntity.getId());
        return rolMapper.toResponse(rolEntity);
    }

    @CacheEvict(cacheNames ="roles", allEntries = true)
    @Override
    public RolResponse createRol(RolRequest rolRequest) {
        rolValidations.validarCamposObligatorios(rolRequest);
        Rol rol = rolMapper.requestToEntity(rolRequest);
        Rol rolResult = rolRepository.save(rol);
        log.info("Se ha guardado el rol {}", rolResult);
        return rolMapper.toResponse(rolResult);
    }

    @CacheEvict(cacheNames ="roles", allEntries = true)
    @Override
    public RolResponse updateRol(Long id, RolRequest rolRequest) {
        rolValidations.validarCampoId(id);
        rolValidations.validarCamposObligatorios(rolRequest);
        Rol rol = rolMapper.requestToEntity(rolRequest);
        rol.setId(id);
        Rol rolResult = rolRepository.save(rol);
        log.info("Modificando rol con id {} " + id, rolResult);
        return rolMapper.toResponse(rolResult);
    }

    @CacheEvict(cacheNames ="roles", allEntries = true)
    @Override
    public void deleteRol(Long id) {
        rolValidations.validarCampoId(id);
        rolRepository.deleteById(id);
        log.info("Eliminando rol con id {}", id);
    }
}
