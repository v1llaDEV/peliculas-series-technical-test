package com.example.demo.validations;

import com.example.demo.dtos.requests.RolRequest;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.repositories.RolRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.RolUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RolValidationsTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolValidations rolValidations;

    @Test
    public void validarCamposObligatoriosTest() {
        assertThatNoException().isThrownBy(() -> {
            rolValidations.validarCamposObligatorios(RolUtils.getRolRequest());
        });
    }

    @Test
    public void validarCamposObligatoriosKOInvalidParametersTest() {
        RolRequest rolRequest = RolUtils.getRolRequest();
        rolRequest.setNombre("");

        assertThatThrownBy(() -> {
            rolValidations.validarCamposObligatorios(rolRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);

        rolRequest.setNombre(null);

        assertThatThrownBy(() -> {
            rolValidations.validarCamposObligatorios(rolRequest);
        }).isInstanceOf(CustomInvalidParameterException.class);
    }

    @Test
    public void validarCampoIdTest() {
        Long id = 1L;
        when(rolRepository.findById(id)).thenReturn(Optional.of(RolUtils.getRol()));

        assertThatNoException().isThrownBy(() -> {
            rolValidations.validarCampoId(id);
        });
    }

}
