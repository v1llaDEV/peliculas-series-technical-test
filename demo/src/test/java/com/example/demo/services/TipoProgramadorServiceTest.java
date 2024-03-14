package com.example.demo.services;

import com.example.demo.dtos.responses.TipoProgramadorResponse;
import com.example.demo.mappers.TipoProgramadorMapper;
import com.example.demo.repositories.TipoProgramadorRepository;
import com.example.demo.services.impl.TipoProgramadorServiceImpl;
import com.example.demo.validations.TipoProgramadorValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.TipoProgramadorUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TipoProgramadorServiceTest {

    @Mock
    private TipoProgramadorRepository tipoProgramadorRepository;

    @Mock
    private TipoProgramadorValidations tipoProgramadorValidations;

    @Mock
    private TipoProgramadorMapper tipoProgramadorMapper;

    @InjectMocks
    private TipoProgramadorServiceImpl tipoProgramadorService;

    @Test
    public void getAllOK() {

        when(tipoProgramadorRepository.findAll()).thenReturn(List.of(TipoProgramadorUtils.getTipoProgramador()));
        when(tipoProgramadorMapper.listToResponse(ArgumentMatchers.any())).thenReturn(TipoProgramadorUtils.getTipoProgramadorResponseList());

        List<TipoProgramadorResponse> listaTipoProgramadorResponse = tipoProgramadorService.getAll();

        assertThat(listaTipoProgramadorResponse, hasSize(1));
    }

    @Test
    public void getEmpleadoByIdTest() {

        when(tipoProgramadorValidations.validarCampoId(Mockito.any())).thenReturn(TipoProgramadorUtils.getTipoProgramador());
        when(tipoProgramadorMapper.toResponse(Mockito.any())).thenReturn(TipoProgramadorUtils.getTipoProgramadorResponse());

        TipoProgramadorResponse tipoProgramadorResponse = tipoProgramadorService.getTipoProgramadorById(1L);

        assertThat(tipoProgramadorResponse, is(notNullValue()));
        assertThat(tipoProgramadorResponse.getNombre(), is(equalTo("test")));
    }

    @Test
    public void createEmpleadoTest() {

        when(tipoProgramadorMapper.toResponse(Mockito.any())).thenReturn(TipoProgramadorUtils.getTipoProgramadorResponse());
        when(tipoProgramadorMapper.requestToEntity(Mockito.any())).thenReturn(TipoProgramadorUtils.getTipoProgramador());

        TipoProgramadorResponse tipoProgramadorResponse = tipoProgramadorService.createTipoProgramador(TipoProgramadorUtils.getTipoProgramadorRequest());

        assertThat(tipoProgramadorResponse, is(notNullValue()));
        assertThat(tipoProgramadorResponse.getNombre(), is(equalTo("test")));
    }

    @Test
    public void updateEmpleadoTest() {

        when(tipoProgramadorMapper.toResponse(Mockito.any())).thenReturn(TipoProgramadorUtils.getTipoProgramadorResponse());
        when(tipoProgramadorMapper.requestToEntity(Mockito.any())).thenReturn(TipoProgramadorUtils.getTipoProgramador());

        TipoProgramadorResponse tipoProgramadorResponse = tipoProgramadorService.updateTipoProgramador(1L, TipoProgramadorUtils.getTipoProgramadorRequest());

        assertThat(tipoProgramadorResponse, is(notNullValue()));
        assertThat(tipoProgramadorResponse.getNombre(), is(equalTo("test")));
    }

    @Test
    public void deleteEmpleadoTest() {

        when(tipoProgramadorValidations.validarCampoId(Mockito.any())).thenReturn(TipoProgramadorUtils.getTipoProgramador());

        assertThatNoException().isThrownBy(() -> {
            tipoProgramadorService.deleteTipoProgramador(1L);
        });
    }
}