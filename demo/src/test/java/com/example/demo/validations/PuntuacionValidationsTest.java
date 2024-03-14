package com.example.demo.validations;

import com.example.demo.dtos.requests.PuntuacionRequest;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.PeliculaSerieRepository;
import com.example.demo.repositories.PuntuacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PuntuacionUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PuntuacionValidationsTest {

    @Mock
    private PuntuacionRepository puntuacionRepository;

    @Mock
    private PeliculaSerieRepository peliculaSerieRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private PuntuacionValidations puntuacionValidations;

    @Test
    public void validarCamposObligatoriosTest() {
        when(peliculaSerieRepository.existsById(Mockito.anyLong())).thenReturn(Boolean.TRUE);
        when(empleadoRepository.existsById(Mockito.anyLong())).thenReturn(Boolean.TRUE);

        assertThatNoException().isThrownBy(() -> {
            puntuacionValidations.validarCamposObligatorios(PuntuacionUtils.getPuntuacionRequest());
        });
    }

    @Test
    public void validarCamposObligatoriosKOEmpleadoNuloTest() {
        PuntuacionRequest puntuacionRequest = PuntuacionUtils.getPuntuacionRequest();
        puntuacionRequest.setEmpleado(null);

        assertThatThrownBy(() -> {
            puntuacionValidations.validarCamposObligatorios(puntuacionRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCamposObligatoriosKOPeliculaNulaTest() {
        PuntuacionRequest puntuacionRequest = PuntuacionUtils.getPuntuacionRequest();
        puntuacionRequest.setPeliculaSerie(null);

        assertThatThrownBy(() -> {
            puntuacionValidations.validarCamposObligatorios(puntuacionRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCamposObligatoriosKOPuntuacionNulaTest() {
        PuntuacionRequest puntuacionRequest = PuntuacionUtils.getPuntuacionRequest();
        puntuacionRequest.setPuntuacion(null);

        assertThatThrownBy(() -> {
            puntuacionValidations.validarCamposObligatorios(puntuacionRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCampoIdTest() {
        Long id = 1L;
        when(puntuacionRepository.findById(id)).thenReturn(Optional.of(PuntuacionUtils.getPuntuacion()));

        assertThatNoException().isThrownBy(() -> {
            puntuacionValidations.validarCampoId(id);
        });
    }

}
