package com.example.demo.validations;

import com.example.demo.constantes.ExceptionsMessageErrors;
import com.example.demo.dtos.requests.PuntuacionRequest;
import com.example.demo.entities.Puntuacion;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.PeliculaSerieRepository;
import com.example.demo.repositories.PuntuacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PuntuacionValidations {

    private final PuntuacionRepository puntuacionRepository;
    private final PeliculaSerieRepository peliculaSerieRepository;
    private final EmpleadoRepository empleadoRepository;

    public void validarCamposObligatorios(PuntuacionRequest puntuacionRequest){
        validarEmpleado(puntuacionRequest);
        validarPeliculaSerie(puntuacionRequest);
        validarPuntuacion(puntuacionRequest);
    }

    public Puntuacion validarCampoId(Long id){
        if(Objects.isNull(id)){
            throw new CustomInvalidParameterException("El campo id es obligatorio");
        }

        return puntuacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                (String.format(ExceptionsMessageErrors.VALIDATION_PUNTUACION_NO_ENCONTRADO_ERROR, id))));
    }

    private void validarPuntuacion(PuntuacionRequest puntuacionRequest){
        if(Objects.isNull(puntuacionRequest.getPuntuacion())){
            throw new CustomInvalidParameterException("No se ha especificado la puntuacion");
        }

        if(puntuacionRequest.getPuntuacion() < 0 || puntuacionRequest.getPuntuacion() > 10){
            throw new CustomInvalidParameterException("La puntuación tiene que estar entre los valores 0 y 10");
        }
    }

    public void validarEmpleado(PuntuacionRequest puntuacionRequest){
        if(Objects.isNull(puntuacionRequest.getEmpleado()) ){
            throw new CustomInvalidParameterException("No se ha especificado el elemento empleado");
        }

        if(Objects.isNull(puntuacionRequest.getEmpleado().getId()) ){
            throw new CustomInvalidParameterException("No se ha especificado el idEmpleado");
        }

        if(!empleadoRepository.existsById(puntuacionRequest.getEmpleado().getId())){
            throw new CustomInvalidParameterException("No existe el empleado con id " + puntuacionRequest.getEmpleado().getId());
        }
    }

    private void validarPeliculaSerie(PuntuacionRequest puntuacionRequest){
        if(Objects.isNull(puntuacionRequest.getPeliculaSerie()) ){
            throw new CustomInvalidParameterException("No se ha especificado el elemento peliculaSerie");
        }

        if(Objects.isNull(puntuacionRequest.getPeliculaSerie().getId()) ){
            throw new CustomInvalidParameterException("No se ha especificado el idPeliculaSerie");
        }

        if(!peliculaSerieRepository.existsById(puntuacionRequest.getPeliculaSerie().getId())){
            throw new CustomInvalidParameterException("No existe la pelicula o serie con id " + puntuacionRequest.getPeliculaSerie().getId());
        }
    }

    public void validacionEmpleadoPeliculaSerieUnicaPuntuacion(PuntuacionRequest puntuacionRequest){
        if(puntuacionRepository.existsByEmpleadoAndPeliculaSerie(puntuacionRequest.getEmpleado(),
                puntuacionRequest.getPeliculaSerie())){
            throw new CustomInvalidParameterException(String.format("El empleado %s ya ha votado la película %s",
                    puntuacionRequest.getEmpleado().getId(), puntuacionRequest.getPeliculaSerie().getId()));
        }
    }

}
