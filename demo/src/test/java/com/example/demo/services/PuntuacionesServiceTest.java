package com.example.demo.services;

import com.example.demo.dtos.responses.PuntuacionResponse;
import com.example.demo.entities.Puntuacion;
import com.example.demo.exceptions.PageParametersInvalidException;
import com.example.demo.mappers.PuntuacionMapper;
import com.example.demo.repositories.PuntuacionRepository;
import com.example.demo.services.impl.PuntuacionServiceImpl;
import com.example.demo.validations.PuntuacionValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utils.PuntuacionUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PuntuacionesServiceTest {

    @Mock
    private PuntuacionRepository puntuacionRepository;

    @Mock
    private PuntuacionValidations puntuacionValidations;

    @Mock
    private PuntuacionMapper puntuacionMapper;

    @InjectMocks
    private PuntuacionServiceImpl puntuacionService;

    @Test
    public void getAllPuntuacionsPaginadoOK() throws PageParametersInvalidException {

        Page<Puntuacion> PuntuacionPage = PuntuacionUtils.getPuntuacionPage();
        List<PuntuacionResponse> PuntuacionResponseList = PuntuacionUtils.getPuntuacionResponseList();

        when(puntuacionRepository.findAll(Mockito.any(Pageable.class))).thenReturn(PuntuacionPage);
        when(puntuacionMapper.listToResponsePage(PuntuacionPage)).thenReturn(PuntuacionResponseList);

        List<PuntuacionResponse> listaPuntuacionsEncontrada = puntuacionService.getAllPuntuacionesPaginado(0, 1);

        assertThat(listaPuntuacionsEncontrada, hasSize(1));
    }

    @Test
    public void getAllPuntuacionsPaginadoPageExceptionKO() throws PageParametersInvalidException {

        assertThatThrownBy(() -> {
            puntuacionService.getAllPuntuacionesPaginado(-1, -1);
        }).isInstanceOf(PageParametersInvalidException.class);

        assertThatThrownBy(() -> {
            puntuacionService.getAllPuntuacionesPaginado(3, -1);
        }).isInstanceOf(PageParametersInvalidException.class);
    }

    @Test
    public void getPuntuacionByIdTest() {

        when(puntuacionValidations.validarCampoId(Mockito.any())).thenReturn(PuntuacionUtils.getPuntuacion());
        when(puntuacionMapper.toResponse(Mockito.any())).thenReturn(PuntuacionUtils.getPuntuacionResponse());

        PuntuacionResponse Puntuacion = puntuacionService.getPuntuacionById(1L);

        assertThat(Puntuacion, is(notNullValue()));
        assertThat(Puntuacion.getPuntuacion(), is(equalTo(7.0)));
    }

    @Test
    public void createPuntuacionTest() {

        when(puntuacionMapper.toResponse(Mockito.any())).thenReturn(PuntuacionUtils.getPuntuacionResponse());
        when(puntuacionMapper.requestToEntity(Mockito.any())).thenReturn(PuntuacionUtils.getPuntuacion());

        PuntuacionResponse Puntuacion = puntuacionService.createPuntuacion(PuntuacionUtils.getPuntuacionRequest());

        assertThat(Puntuacion, is(notNullValue()));
        assertThat(Puntuacion.getPuntuacion(), is(equalTo(7.0)));
    }

    @Test
    public void updatePuntuacionTest() {

        when(puntuacionMapper.toResponse(Mockito.any())).thenReturn(PuntuacionUtils.getPuntuacionResponse());
        when(puntuacionRepository.findByEmpleadoAndPeliculaSerie(Mockito.any(), Mockito.any())).thenReturn(PuntuacionUtils.getPuntuacionOptional());
        when(puntuacionRepository.save(Mockito.any())).thenReturn(PuntuacionUtils.getPuntuacion());

        PuntuacionResponse Puntuacion = puntuacionService.updatePuntuacion(PuntuacionUtils.getPuntuacionRequest());

        assertThat(Puntuacion, is(notNullValue()));
        assertThat(Puntuacion.getPuntuacion(), is(equalTo(7.0)));
    }

    @Test
    public void deletePuntuacionTest() {

        when(puntuacionValidations.validarCampoId(Mockito.any())).thenReturn(PuntuacionUtils.getPuntuacion());

        assertThatNoException().isThrownBy(() -> {
            puntuacionService.deletePuntuacion(1L);
        });
    }
}