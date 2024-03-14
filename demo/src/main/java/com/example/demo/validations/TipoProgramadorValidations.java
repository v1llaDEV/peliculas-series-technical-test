package com.example.demo.validations;

import com.example.demo.constantes.ExceptionsMessageErrors;
import com.example.demo.dtos.requests.TipoProgramadorRequest;
import com.example.demo.entities.TipoProgramador;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.TipoProgramadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TipoProgramadorValidations {

    private final TipoProgramadorRepository tipoProgramadorRepository;

    public void validarCamposObligatorios(TipoProgramadorRequest rolRequest){
        validarCampoNombre(rolRequest);
    }

    public TipoProgramador validarCampoId(Long id){
        if(Objects.isNull(id)){
            throw new CustomInvalidParameterException("El campo id es obligatorio");
        }

        return tipoProgramadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                (String.format(ExceptionsMessageErrors.VALIDATION_TIPO_PROGRAMADOR_NO_ENCONTRADO_ERROR, id))));
    }

    private void validarCampoNombre(TipoProgramadorRequest tipoProgramadorRequest){
        if(Objects.isNull(tipoProgramadorRequest.getNombre()) || tipoProgramadorRequest.getNombre().isBlank()){
            throw new CustomInvalidParameterException("El campo nombre es obligatorio");
        }

        if(tipoProgramadorRepository.existsByNombre(tipoProgramadorRequest.getNombre())){
            throw new CustomInvalidParameterException("Ya existe un tipoProgramador con el nombre " + tipoProgramadorRequest.getNombre());
        }
    }

}
