package com.example.demo.validations;

import com.example.demo.dtos.requests.EmpleadoRequest;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.RolRepository;
import com.example.demo.repositories.TipoProgramadorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.EmpleadoUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EmpleadoValidationsTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private TipoProgramadorRepository tipoProgramadorRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private EmpleadoValidations empleadoValidations;

    @Test
    public void validarCamposObligatoriosTest() {
        when(tipoProgramadorRepository.existsById(Mockito.anyLong())).thenReturn(Boolean.TRUE);
        when(rolRepository.existsById(Mockito.anyLong())).thenReturn(Boolean.TRUE);

        assertThatNoException().isThrownBy(() -> {
            empleadoValidations.validarCamposObligatorios(EmpleadoUtils.getEmpleadoRequest());
        });
    }

    @Test
    public void validarCamposObligatoriosKONombreNuloTest() {
        EmpleadoRequest empleadoRequest = EmpleadoUtils.getEmpleadoRequest();
        empleadoRequest.setNombre("");

        assertThatThrownBy(() -> {
            empleadoValidations.validarCamposObligatorios(empleadoRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);

        empleadoRequest.setNombre(null);

        assertThatThrownBy(() -> {
            empleadoValidations.validarCamposObligatorios(empleadoRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);

    }

    @Test
    public void validarCamposObligatoriosKOEmailNuloTest() {
        EmpleadoRequest empleadoRequest = EmpleadoUtils.getEmpleadoRequest();
        empleadoRequest.setEmail(null);

        assertThatThrownBy(() -> {
            empleadoValidations.validarCamposObligatorios(empleadoRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCamposObligatoriosKOEmailInvalidoTest() {
        EmpleadoRequest empleadoRequest = EmpleadoUtils.getEmpleadoRequest();
        empleadoRequest.setEmail("test");

        assertThatThrownBy(() -> {
            empleadoValidations.validarCamposObligatorios(empleadoRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCamposObligatoriosKOPasswordNulaTest() {
        EmpleadoRequest empleadoRequest = EmpleadoUtils.getEmpleadoRequest();
        empleadoRequest.setPassword(null);

        assertThatThrownBy(() -> {
            empleadoValidations.validarCamposObligatorios(empleadoRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCampoIdTest() {
        Long id = 1L;
        when(empleadoRepository.findById(id)).thenReturn(Optional.of(EmpleadoUtils.getEmpleado()));

        assertThatNoException().isThrownBy(() -> {
            empleadoValidations.validarCampoId(id);
        });
    }

}
