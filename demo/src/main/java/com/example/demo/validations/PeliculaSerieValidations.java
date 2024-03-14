package com.example.demo.validations;

import com.example.demo.constantes.ExceptionsMessageErrors;
import com.example.demo.dtos.requests.PeliculaSerieRequest;
import com.example.demo.entities.PeliculaSerie;
import com.example.demo.exceptions.CustomInvalidParameterException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.PeliculaSerieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.Year;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PeliculaSerieValidations {

    private final PeliculaSerieRepository peliculaSerieRepository;
    private final EmpleadoRepository empleadoRepository;

    public void validarCamposObligatorios(PeliculaSerieRequest peliculaSerieRequest){
        validarCampoTitulo(peliculaSerieRequest);
        validarCampoAnio(peliculaSerieRequest);
        validarCampoDirector(peliculaSerieRequest);
        validarCampoGenero(peliculaSerieRequest);
        validarCampoTemporadasDuracion(peliculaSerieRequest);
        validarCampoNotaMedia(peliculaSerieRequest);
        validaCampoEmpleadoImplantacion(peliculaSerieRequest);
        validaCampoEmpleadoProposicion(peliculaSerieRequest);
    }

    public PeliculaSerie validarCampoId(Long id){
        if(Objects.isNull(id)){
            throw new CustomInvalidParameterException("El campo id es obligatorio");
        }

        return peliculaSerieRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                (String.format(ExceptionsMessageErrors.VALIDATION_PELICULA_SERIE_NO_ENCONTRADA_ERROR, id))));
    }

    private void validarCampoTitulo(PeliculaSerieRequest peliculaSerieRequest){
        if(Objects.isNull(peliculaSerieRequest.getTitulo()) || peliculaSerieRequest.getTitulo().isBlank()){
            throw new CustomInvalidParameterException("El campo nombre es obligatorio");
        }

        if(peliculaSerieRepository.existsByTitulo(peliculaSerieRequest.getTitulo())){
            throw new CustomInvalidParameterException("Ya existe una pelicula o serie con el título " + peliculaSerieRequest.getTitulo());
        }
    }

    private void validarCampoAnio(PeliculaSerieRequest peliculaSerieRequest){
        if(Objects.isNull(peliculaSerieRequest.getAnio())){
            throw new CustomInvalidParameterException("El campo año es obligatorio");
        }

        if(peliculaSerieRequest.getAnio() < 1800 || peliculaSerieRequest.getAnio() > Year.now().getValue()){
            throw new CustomInvalidParameterException("El campo año es debe estar entre 1800 y el año actual");
        }
    }

    private void validarCampoDirector(PeliculaSerieRequest peliculaSerieRequest){
        if(Objects.isNull(peliculaSerieRequest.getDirector()) || peliculaSerieRequest.getDirector().isBlank()){
            throw new CustomInvalidParameterException("El campo director es obligatorio");
        }
    }

    private void validarCampoGenero(PeliculaSerieRequest peliculaSerieRequest){
        if(Objects.isNull(peliculaSerieRequest.getGenero()) || peliculaSerieRequest.getGenero().isBlank()){
            throw new CustomInvalidParameterException("El campo género es obligatorio");
        }
    }

    private void validarCampoNotaMedia(PeliculaSerieRequest peliculaSerieRequest){
        if(Objects.isNull(peliculaSerieRequest.getNotaMedia())){
            throw new CustomInvalidParameterException("El campo nota media es obligatorio");
        }

        if(peliculaSerieRequest.getNotaMedia() < 0 || peliculaSerieRequest.getNotaMedia() > 10){
            throw new CustomInvalidParameterException("El campo nota media debe estar entre 0 y 10");
        }
    }

    private void validaCampoEmpleadoImplantacion(PeliculaSerieRequest peliculaSerieRequest){
        if(Objects.isNull(peliculaSerieRequest.getEmpleadoImplantacion()) ){
            throw new CustomInvalidParameterException("No se ha especificado el elemento empleado implantacion");
        }

        if(Objects.isNull(peliculaSerieRequest.getEmpleadoImplantacion().getId()) ){
            throw new CustomInvalidParameterException("No se ha especificado el id de empleado implantación");
        }

        if(!empleadoRepository.existsById(peliculaSerieRequest.getEmpleadoImplantacion().getId())){
            throw new CustomInvalidParameterException("No existe el empleado con id " + peliculaSerieRequest.getEmpleadoImplantacion().getId());
        }
    }

    private void validaCampoEmpleadoProposicion(PeliculaSerieRequest peliculaSerieRequest){
        if(Objects.isNull(peliculaSerieRequest.getEmpleadoProposicion()) ){
            throw new CustomInvalidParameterException("No se ha especificado el elemento empleado proposicion");
        }

        if(Objects.isNull(peliculaSerieRequest.getEmpleadoProposicion().getId()) ){
            throw new CustomInvalidParameterException("No se ha especificado el id de empleado proposición");
        }

        if(!empleadoRepository.existsById(peliculaSerieRequest.getEmpleadoProposicion().getId())){
            throw new CustomInvalidParameterException("No existe el empleado con id " + peliculaSerieRequest.getEmpleadoProposicion().getId());
        }
    }

    private void validarCampoTemporadasDuracion(PeliculaSerieRequest peliculaSerieRequest) throws CustomInvalidParameterException {
        if(Objects.isNull(peliculaSerieRequest.getTemporadas()) &&
                Objects.isNull(peliculaSerieRequest.getDuracion())){
            throw new CustomInvalidParameterException("Debe rellenar el número de temporadas en caso de estar" +
                    " especificando una serie o la duración en caso de ser una película");
        }

        if(!Objects.isNull(peliculaSerieRequest.getTemporadas()) &&
                !Objects.isNull(peliculaSerieRequest.getDuracion())){
            throw new CustomInvalidParameterException("No puede especificar temporadas y duración para el recurso pelicula o serie. " +
                    "Si es una serie debe especificar solo las temporadas y si es una película especifique la duración");
        }

        if(!Objects.isNull(peliculaSerieRequest.getTemporadas()) && (peliculaSerieRequest.getTemporadas() < 1 || peliculaSerieRequest.getTemporadas() > 100)){
            throw new CustomInvalidParameterException("El número de temporadas debe estar entre 1 y 100");
        }

        if(!Objects.isNull(peliculaSerieRequest.getDuracion()) &&
                (peliculaSerieRequest.getDuracion().isBefore(LocalTime.of(0, 10, 0))
                        || peliculaSerieRequest.getDuracion().isAfter(LocalTime.of(5, 0, 0)))){
            throw new CustomInvalidParameterException("La duración de una película debe estar entre 10 minutos y 5 horas");
        }

    }



}
