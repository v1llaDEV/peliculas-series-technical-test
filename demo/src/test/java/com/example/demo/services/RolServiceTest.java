package com.example.demo.services;

import com.example.demo.dtos.responses.RolResponse;
import com.example.demo.mappers.RolMapper;
import com.example.demo.repositories.RolRepository;
import com.example.demo.services.impl.RolServiceImpl;
import com.example.demo.validations.RolValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.RolUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @Mock
    private RolValidations rolValidations;

    @Mock
    private RolMapper rolMapper;

    @InjectMocks
    private RolServiceImpl rolService;

    @Test
    public void getAllOK() {

        when(rolRepository.findAll()).thenReturn(List.of(RolUtils.getRol()));
        when(rolMapper.listToResponse(ArgumentMatchers.any())).thenReturn(RolUtils.getRolResponseList());

        List<RolResponse> listaRolResponse = rolService.getAll();

        assertThat(listaRolResponse, hasSize(1));
    }

    @Test
    public void getEmpleadoByIdTest() {

        when(rolValidations.validarCampoId(Mockito.any())).thenReturn(RolUtils.getRol());
        when(rolMapper.toResponse(Mockito.any())).thenReturn(RolUtils.getRolResponse());

        RolResponse rolResponse = rolService.getRolById(1L);

        assertThat(rolResponse, is(notNullValue()));
        assertThat(rolResponse.getNombre(), is(equalTo("test")));
    }

    @Test
    public void createEmpleadoTest() {

        when(rolMapper.toResponse(Mockito.any())).thenReturn(RolUtils.getRolResponse());
        when(rolMapper.requestToEntity(Mockito.any())).thenReturn(RolUtils.getRol());

        RolResponse rol = rolService.createRol(RolUtils.getRolRequest());

        assertThat(rol, is(notNullValue()));
        assertThat(rol.getNombre(), is(equalTo("test")));
    }

    @Test
    public void updateEmpleadoTest() {

        when(rolMapper.toResponse(Mockito.any())).thenReturn(RolUtils.getRolResponse());
        when(rolMapper.requestToEntity(Mockito.any())).thenReturn(RolUtils.getRol());

        RolResponse rol = rolService.updateRol(1L, RolUtils.getRolRequest());

        assertThat(rol, is(notNullValue()));
        assertThat(rol.getNombre(), is(equalTo("test")));
    }

    @Test
    public void deleteEmpleadoTest() {

        when(rolValidations.validarCampoId(Mockito.any())).thenReturn(RolUtils.getRol());

        assertThatNoException().isThrownBy(() -> {
            rolService.deleteRol(1L);
        });
    }
}