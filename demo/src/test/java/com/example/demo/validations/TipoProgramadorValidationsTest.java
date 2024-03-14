package com.example.demo.validations;

import com.example.demo.dtos.requests.TipoProgramadorRequest;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.repositories.TipoProgramadorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.TipoProgramadorUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TipoProgramadorValidationsTest {

    @Mock
    private TipoProgramadorRepository tipoProgramadorRepository;

    @InjectMocks
    private TipoProgramadorValidations tipoProgramadorValidations;

    @Test
    public void validarCamposObligatoriosTest() {
        assertThatNoException().isThrownBy(() -> {
            tipoProgramadorValidations.validarCamposObligatorios(TipoProgramadorUtils.getTipoProgramadorRequest());
        });
    }

    @Test
    public void validarCamposObligatoriosKONombreNuloOVacioTest() {
        TipoProgramadorRequest tipoProgramadorRequest = TipoProgramadorUtils.getTipoProgramadorRequest();
        tipoProgramadorRequest.setNombre("");

        assertThatThrownBy(() -> {
            tipoProgramadorValidations.validarCamposObligatorios(tipoProgramadorRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);

        tipoProgramadorRequest.setNombre(null);

        assertThatThrownBy(() -> {
            tipoProgramadorValidations.validarCamposObligatorios(tipoProgramadorRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCamposObligatoriosKOInvalidParametersTest() {
        TipoProgramadorRequest tipoProgramadorRequest = TipoProgramadorUtils.getTipoProgramadorRequest();
        tipoProgramadorRequest.setNombre(null);

        assertThatThrownBy(() -> {
            tipoProgramadorValidations.validarCamposObligatorios(tipoProgramadorRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);

        tipoProgramadorRequest.setNombre("");

        assertThatThrownBy(() -> {
            tipoProgramadorValidations.validarCamposObligatorios(tipoProgramadorRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCampoIdTest() {
        Long id = 1L;
        when(tipoProgramadorRepository.findById(id)).thenReturn(Optional.of(TipoProgramadorUtils.getTipoProgramador()));

        assertThatNoException().isThrownBy(() -> {
            tipoProgramadorValidations.validarCampoId(id);
        });
    }

}
