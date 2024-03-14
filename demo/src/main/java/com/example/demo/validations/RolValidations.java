package com.example.demo.validations;

import com.example.demo.constantes.ExceptionsMessageErrors;
import com.example.demo.dtos.requests.RolRequest;
import com.example.demo.entities.Rol;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RolValidations {

    private final RolRepository rolRepository;

    public void validarCamposObligatorios(RolRequest rolRequest){
        validarCampoNombre(rolRequest);
    }

    public Rol validarCampoId(Long id){
        if(Objects.isNull(id)){
            throw new CustomInvalidParameterException("El campo id es obligatorio");
        }

        return rolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                (String.format(ExceptionsMessageErrors.VALIDATION_ROL_NO_ENCONTRADO_ERROR, id))));
    }

    private void validarCampoNombre(RolRequest rolRequest){
        if(Objects.isNull(rolRequest.getNombre()) || rolRequest.getNombre().isBlank()){
            throw new CustomInvalidParameterException("El campo nombre es obligatorio");
        }

        if(rolRepository.existsByNombre(rolRequest.getNombre())){
            throw new CustomInvalidParameterException("Ya existe un rol con el nombre " + rolRequest.getNombre());
        }
    }

}
