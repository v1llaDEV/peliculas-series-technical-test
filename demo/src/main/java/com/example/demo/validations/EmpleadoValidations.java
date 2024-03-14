package com.example.demo.validations;

import com.example.demo.constantes.ExceptionsMessageErrors;
import com.example.demo.dtos.requests.EmpleadoRequest;
import com.example.demo.entities.Empleado;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.exceptions.EmpleadoEmailYaExistenteException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.RolRepository;
import com.example.demo.repositories.TipoProgramadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class EmpleadoValidations {

    private final EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;
    private final TipoProgramadorRepository tipoProgramadorRepository;

    public void validarCamposObligatorios(EmpleadoRequest empleadoRequest){
        validarCampoNombre(empleadoRequest);
        validarCampoEmail(empleadoRequest);
        validarCampoPassword(empleadoRequest);
        validarFechaNacimiento(empleadoRequest);
        validarTipoProgramador(empleadoRequest);
        validarRol(empleadoRequest);
    }

    public Empleado validarCampoId(Long id){
        if(Objects.isNull(id)){
            throw new CustomInvalidParameterException("El campo id es obligatorio");
        }

        return empleadoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                (String.format(ExceptionsMessageErrors.VALIDATION_EMPLEADO_NO_ENCONTRADO_ERROR, id))));
    }

    private void validarCampoNombre(EmpleadoRequest empleadoRequest){
        if(Objects.isNull(empleadoRequest.getNombre()) || empleadoRequest.getNombre().isBlank()){
            throw new CustomInvalidParameterException("El campo nombre es obligatorio");
        }
    }

    private void validarCampoEmail(EmpleadoRequest empleadoRequest){
        if(Objects.isNull(empleadoRequest.getEmail()) || empleadoRequest.getEmail().isBlank()){
            throw new CustomInvalidParameterException("El campo email es obligatorio");
        }

        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if(! Pattern.compile(regexPattern)
                .matcher(empleadoRequest.getEmail())
                .matches()){
            throw new CustomInvalidParameterException("El campo email tiene un formato incorrecto");
        }

        if(empleadoRepository.existsByEmail(empleadoRequest.getEmail())){
            throw new EmpleadoEmailYaExistenteException(String.format("Ya existe un empleado con email: %s", empleadoRequest.getEmail()));
        }
    }

    private void validarCampoPassword(EmpleadoRequest empleadoRequest){
        if(Objects.isNull(empleadoRequest.getPassword()) || empleadoRequest.getPassword().isBlank()){
            throw new CustomInvalidParameterException("El campo password es obligatorio");
        }
    }

    private void validarFechaNacimiento(EmpleadoRequest empleadoRequest){
        if(Objects.isNull(empleadoRequest.getFechaNacimiento()) ){
            throw new CustomInvalidParameterException("El campo fechaNacimiento es obligatorio");
        }
    }

    private void validarRol(EmpleadoRequest empleadoRequest){
        if(Objects.isNull(empleadoRequest.getRol()) ){
            throw new CustomInvalidParameterException("No se ha especificado el elemento rol junto con su id");
        }

        if(Objects.isNull(empleadoRequest.getRol().getId()) ){
            throw new CustomInvalidParameterException("No se ha especificado el campo rol.id");
        }

        if(!rolRepository.existsById(empleadoRequest.getRol().getId())){
            throw new CustomInvalidParameterException("No existe el rol id " + empleadoRequest.getRol().getId());
        }
    }
    private void validarTipoProgramador(EmpleadoRequest empleadoRequest){
        if(Objects.isNull(empleadoRequest.getTipoProgramador()) ){
            throw new CustomInvalidParameterException("No se ha especificado el elemento tipoProgramador junto con su id");
        }

        if(Objects.isNull(empleadoRequest.getTipoProgramador().getId()) ){
            throw new CustomInvalidParameterException("No se ha especificado el campo tipoProgramador.id");
        }

        if(!tipoProgramadorRepository.existsById(empleadoRequest.getTipoProgramador().getId())){
            throw new CustomInvalidParameterException("No existe el tipoProgramador id " + empleadoRequest.getTipoProgramador().getId());
        }
    }

}
