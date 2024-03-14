package com.example.demo.services;

import com.example.demo.dtos.responses.EmpleadoResponse;
import com.example.demo.entities.Empleado;
import com.example.demo.exceptions.PageParametersInvalidException;
import com.example.demo.mappers.EmpleadoMapper;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.services.impl.EmpleadoServiceImpl;
import com.example.demo.validations.EmpleadoValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import utils.EmpleadoUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private EmpleadoValidations empleadoValidations;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private EmpleadoMapper empleadoMapper;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    @Test
    public void getAllEmpleadosPaginadoOK() throws PageParametersInvalidException {

        Page<Empleado> empleadoPage = EmpleadoUtils.getEmpleadoPage();
        List<EmpleadoResponse> empleadoResponseList = EmpleadoUtils.getEmpleadoResponseList();

        when(empleadoRepository.findAll(Mockito.any(Pageable.class))).thenReturn(empleadoPage);
        when(empleadoMapper.listToResponsePage(empleadoPage)).thenReturn(empleadoResponseList);

        List<EmpleadoResponse> listaEmpleadosEncontrada = empleadoService.getAllEmpleadosPaginado(0, 1);

        assertThat(listaEmpleadosEncontrada, hasSize(1));
    }

    @Test
    public void getAllEmpleadosPaginadoPageExceptionKO() throws PageParametersInvalidException {

        assertThatThrownBy(() -> {
            empleadoService.getAllEmpleadosPaginado(-1, -1);
        }).isInstanceOf(PageParametersInvalidException.class);

        assertThatThrownBy(() -> {
            empleadoService.getAllEmpleadosPaginado(3, -1);
        }).isInstanceOf(PageParametersInvalidException.class);
    }

    @Test
    public void getEmpleadoByIdTest() {

        when(empleadoValidations.validarCampoId(Mockito.any())).thenReturn(EmpleadoUtils.getEmpleado());
        when(empleadoMapper.toResponse(Mockito.any())).thenReturn(EmpleadoUtils.getEmpleadoResponse());

        EmpleadoResponse empleado = empleadoService.getEmpleadoById(1L);

        assertThat(empleado, is(notNullValue()));
        assertThat(empleado.getNombre(), is(equalTo("test")));
    }

    @Test
    public void createEmpleadoTest() {

        when(empleadoMapper.toResponse(Mockito.any())).thenReturn(EmpleadoUtils.getEmpleadoResponse());
        when(empleadoMapper.requestToEntity(Mockito.any())).thenReturn(EmpleadoUtils.getEmpleado());
        when(passwordEncoder.encode(Mockito.any())).thenReturn(anyString());

        EmpleadoResponse empleado = empleadoService.createEmpleado(EmpleadoUtils.getEmpleadoRequest());

        assertThat(empleado, is(notNullValue()));
        assertThat(empleado.getNombre(), is(equalTo("test")));
    }

    @Test
    public void updateEmpleadoTest() {

        when(empleadoMapper.toResponse(Mockito.any())).thenReturn(EmpleadoUtils.getEmpleadoResponse());
        when(empleadoMapper.requestToEntity(Mockito.any())).thenReturn(EmpleadoUtils.getEmpleado());
        when(passwordEncoder.encode(Mockito.any())).thenReturn(anyString());

        EmpleadoResponse empleado = empleadoService.updateEmpleado(1L, EmpleadoUtils.getEmpleadoRequest());

        assertThat(empleado, is(notNullValue()));
        assertThat(empleado.getNombre(), is(equalTo("test")));
    }

    @Test
    public void deleteEmpleadoTest() {

        when(empleadoValidations.validarCampoId(Mockito.any())).thenReturn(EmpleadoUtils.getEmpleado());

        assertThatNoException().isThrownBy(() -> {
            empleadoService.deleteEmpleado(1L);
        });
    }
}