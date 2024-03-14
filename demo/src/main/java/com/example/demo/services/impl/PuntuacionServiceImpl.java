package com.example.demo.services.impl;

import com.example.demo.dtos.requests.PuntuacionRequest;
import com.example.demo.dtos.responses.PuntuacionResponse;
import com.example.demo.entities.Empleado;
import com.example.demo.entities.PeliculaSerie;
import com.example.demo.entities.Puntuacion;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.exceptions.PageParametersInvalidException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mappers.EmpleadoMapper;
import com.example.demo.mappers.PuntuacionMapper;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.PeliculaSerieRepository;
import com.example.demo.repositories.PuntuacionRepository;
import com.example.demo.services.PuntuacionService;
import com.example.demo.validations.EmpleadoValidations;
import com.example.demo.validations.PuntuacionValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Service
public class PuntuacionServiceImpl implements PuntuacionService {

    private final PuntuacionRepository puntuacionRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PeliculaSerieRepository peliculaSerieRepository;
    private final PuntuacionValidations puntuacionValidations;
    private final EmpleadoValidations empleadoValidations;
    private final PuntuacionMapper puntuacionMapper;
    private final EmpleadoMapper empleadoMapper;

    @Cacheable("puntuaciones")
    @Override
    public List<PuntuacionResponse> getAllPuntuacionesPaginado(int pagina, int tamanio) throws PageParametersInvalidException {
        if (pagina < 0) {
            throw new PageParametersInvalidException("El parámetro pagina no puede ser menor que 0");
        } else if (tamanio < 1) {
            throw new PageParametersInvalidException("El parámetro tamanio no puede ser menor que 1");
        }

        PageRequest pageRequest = PageRequest.of(pagina, tamanio);
        Page<Puntuacion> puntuacionesResult = puntuacionRepository.findAll(pageRequest);
        log.info("Consultando todas las puntuaciones {}", puntuacionesResult);
        return puntuacionMapper.listToResponsePage(puntuacionesResult);
    }

    @Cacheable("puntuaciones")
    @Override
    public PuntuacionResponse getPuntuacionById(Long id) {
        Puntuacion puntuacionEntity = puntuacionValidations.validarCampoId(id);
        log.info("Consultando puntuaciones por id {}", puntuacionEntity.getId());
        return puntuacionMapper.toResponse(puntuacionEntity);
    }

    /* Mediante esta implementación si un empleado ha votado más de una película con la máxima nota
       solo se devuelve una. Habría que revisar qué es lo que se requiere exactamente
    */
    //TODO: Habría que encontrar una forma más óptima de realizar esta implementación
    @Override
    public PuntuacionResponse getMaxPuntuacionByIdEmpleado(Long idEmpleado) {
        empleadoValidations.validarCampoId(idEmpleado);
        List<Map> maxPuntuacionByEmpleado = puntuacionRepository.findMaxPuntuacionByEmpleado(idEmpleado);
        List<PuntuacionResponse> listPuntuaciones = new ArrayList<>();
        maxPuntuacionByEmpleado.forEach(elementoPuntuacion -> {
            PuntuacionResponse puntuacionResponse = PuntuacionResponse.builder().puntuacion((Double) elementoPuntuacion.get("puntuacionMaxima"))
                    .empleado(Empleado.builder().id((Long) elementoPuntuacion.get("empleadoId")).build())
                    .peliculaSerie(PeliculaSerie.builder().id((Long) elementoPuntuacion.get("peliculaId")).build())
                    .build();
            listPuntuaciones.add(puntuacionResponse);
        });

        PuntuacionResponse maximoResultado = listPuntuaciones.stream()
                .max(Comparator.comparing(PuntuacionResponse::getPuntuacion))
                .orElseThrow( () -> new ResourceNotFoundException("No se ha encontrado ninguna nota para este empleado") );

        Empleado empleado = empleadoRepository.findById(maximoResultado.getEmpleado().getId()).get();
        maximoResultado.setEmpleado(empleado);
        PeliculaSerie peliculaSerie =peliculaSerieRepository.findById(maximoResultado.getPeliculaSerie().getId()).get();
        maximoResultado.setPeliculaSerie(peliculaSerie);


        log.info("Consultando puntuaciones máxima por id empleado {}", idEmpleado);
        return maximoResultado;
    }

    @CacheEvict(cacheNames ="puntuaciones", allEntries = true)
    @Override
    public PuntuacionResponse createPuntuacion(PuntuacionRequest puntuacionRequest) {
        puntuacionValidations.validarCamposObligatorios(puntuacionRequest);
        puntuacionValidations.validacionEmpleadoPeliculaSerieUnicaPuntuacion(puntuacionRequest);
        Puntuacion puntuacionResult = puntuacionRepository.save(puntuacionMapper.requestToEntity(puntuacionRequest));
        log.info("Se ha guardado la puntuacion {}", puntuacionResult);
        return puntuacionMapper.toResponse(puntuacionResult);
    }

    @CacheEvict(cacheNames ="puntuaciones", allEntries = true)
    @Override
    public PuntuacionResponse updatePuntuacion(PuntuacionRequest puntuacionRequest) {
        puntuacionValidations.validarCamposObligatorios(puntuacionRequest);
        Puntuacion puntuacionEntity = puntuacionRepository.findByEmpleadoAndPeliculaSerie(puntuacionRequest.getEmpleado(),
                        puntuacionRequest.getPeliculaSerie())
                .orElseThrow(() -> new CustomInvalidParameterException(String.format("No se ha encontrado la puntuación" +
                                "del empleado %s sobre la película %s", puntuacionRequest.getEmpleado().getId(),
                        puntuacionRequest.getPeliculaSerie().getId())));
        puntuacionEntity.setPuntuacion(puntuacionRequest.getPuntuacion());
        Puntuacion puntuacionResult = puntuacionRepository.save(puntuacionEntity);
        log.info("Modificando puntuacion con id {}", puntuacionResult.getId());
        return puntuacionMapper.toResponse(puntuacionResult);
    }

    @CacheEvict(cacheNames ="puntuaciones", allEntries = true)
    @Override
    public void deletePuntuacion(Long id) {
        puntuacionValidations.validarCampoId(id);
        puntuacionRepository.deleteById(id);
        log.info("Eliminando puntuacion con id {}", id);
    }
}
