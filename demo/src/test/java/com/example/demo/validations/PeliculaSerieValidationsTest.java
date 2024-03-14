package com.example.demo.validations;

import com.example.demo.dtos.requests.PeliculaSerieRequest;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.PeliculaSerieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.PeliculaSerieUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PeliculaSerieValidationsTest {

    @Mock
    private PeliculaSerieRepository peliculaSerieRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;


    @InjectMocks
    private PeliculaSerieValidations peliculaSerieValidations;

    @Test
    public void validarCamposObligatoriosTest() {
        when(empleadoRepository.existsById(Mockito.anyLong())).thenReturn(Boolean.TRUE);

        assertThatNoException().isThrownBy(() -> {
            peliculaSerieValidations.validarCamposObligatorios(PeliculaSerieUtils.getPeliculaSerieRequest());
        });
    }

    @Test
    public void validarCamposObligatoriosKOTituloNuloTest() {
        PeliculaSerieRequest peliculaSerieRequest = PeliculaSerieUtils.getPeliculaSerieRequest();
        peliculaSerieRequest.setTitulo("");

        assertThatThrownBy(() -> {
            peliculaSerieValidations.validarCamposObligatorios(peliculaSerieRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);

        peliculaSerieRequest.setTitulo(null);

        assertThatThrownBy(() -> {
            peliculaSerieValidations.validarCamposObligatorios(peliculaSerieRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);

    }

    @Test
    public void validarCamposObligatoriosKODirectorNuloTest() {
        PeliculaSerieRequest PeliculaSerieRequest = PeliculaSerieUtils.getPeliculaSerieRequest();
        PeliculaSerieRequest.setDirector(null);

        assertThatThrownBy(() -> {
            peliculaSerieValidations.validarCamposObligatorios(PeliculaSerieRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCamposObligatoriosKOEmailInvalidoTest() {
        PeliculaSerieRequest PeliculaSerieRequest = PeliculaSerieUtils.getPeliculaSerieRequest();
        PeliculaSerieRequest.setDirector("test");

        assertThatThrownBy(() -> {
            peliculaSerieValidations.validarCamposObligatorios(PeliculaSerieRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCamposObligatoriosKOGeneroNulaTest() {
        PeliculaSerieRequest PeliculaSerieRequest = PeliculaSerieUtils.getPeliculaSerieRequest();
        PeliculaSerieRequest.setGenero(null);

        assertThatThrownBy(() -> {
            peliculaSerieValidations.validarCamposObligatorios(PeliculaSerieRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCampoIdTest() {
        Long id = 1L;
        when(peliculaSerieRepository.findById(id)).thenReturn(Optional.of(PeliculaSerieUtils.getPeliculaSerie()));

        assertThatNoException().isThrownBy(() -> {
            peliculaSerieValidations.validarCampoId(id);
        });
    }

}
