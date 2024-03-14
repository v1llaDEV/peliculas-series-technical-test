package com.example.demo.services.impl;

import com.example.demo.dtos.requests.EmpleadoRequest;
import com.example.demo.dtos.responses.EmpleadoResponse;
import com.example.demo.entities.Empleado;
import com.example.demo.exceptions.PageParametersInvalidException;
import com.example.demo.mappers.EmpleadoMapper;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.services.EmpleadoService;
import com.example.demo.validations.EmpleadoValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoValidations empleadoValidations;
    private final EmpleadoMapper empleadoMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Cacheable("empleados")
    @Override
    public List<EmpleadoResponse> getAllEmpleadosPaginado(int pagina, int tamanio) throws PageParametersInvalidException {
        if (pagina < 0) {
            throw new PageParametersInvalidException("El parámetro pagina no puede ser menor que 0");
        } else if (tamanio < 1) {
            throw new PageParametersInvalidException("El parámetro tamanio no puede ser menor que 1");
        }

        PageRequest pageRequest = PageRequest.of(pagina, tamanio);
        Page<Empleado> empleadosResult = empleadoRepository.findAll(pageRequest);
        log.info("Consultando empleados paginado {}", empleadosResult);
        return empleadoMapper.listToResponsePage(empleadosResult);
    }

    @Cacheable("empleados")
    @Override
    public EmpleadoResponse getEmpleadoById(Long id) {
        Empleado empleadoEntity = empleadoValidations.validarCampoId(id);
        log.info("Consultando empleados por id {}", empleadoEntity.getId());
        return empleadoMapper.toResponse(empleadoEntity);
    }

    @CacheEvict(cacheNames ="empleados", allEntries = true)
    @Override
    public EmpleadoResponse createEmpleado(EmpleadoRequest empleadoRequest) {
        empleadoValidations.validarCamposObligatorios(empleadoRequest);
        Empleado empleado = empleadoMapper.requestToEntity(empleadoRequest);
        String password = empleadoRequest.getPassword();
        empleado.setPassword(passwordEncoder.encode(password));
        Empleado empleadoResult = empleadoRepository.save(empleado);
        log.info("Se ha guardado el empleado {}", empleadoResult);
        return empleadoMapper.toResponse(empleadoResult);
    }

    @CacheEvict(cacheNames ="empleados", allEntries = true)
    @Override
    public EmpleadoResponse updateEmpleado(Long id, EmpleadoRequest empleadoRequest) {
        empleadoValidations.validarCampoId(id);
        empleadoValidations.validarCamposObligatorios(empleadoRequest);
        Empleado empleado = empleadoMapper.requestToEntity(empleadoRequest);
        empleado.setId(id);
        String password = empleadoRequest.getPassword();
        empleado.setPassword(passwordEncoder.encode(password));
        Empleado empleadoResult = empleadoRepository.save(empleado);
        log.info("Modificando empleado con id " + id + ": %s", empleadoResult);
        return empleadoMapper.toResponse(empleadoResult);
    }

    @CacheEvict(cacheNames ="empleados", allEntries = true)
    @Override
    public void deleteEmpleado(Long id) {
        empleadoValidations.validarCampoId(id);
        empleadoRepository.deleteById(id);
        log.info("Eliminando empleado con id " + id);
    }
}
