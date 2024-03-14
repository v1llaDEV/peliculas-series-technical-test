package com.example.demo.services;

import com.example.demo.dtos.responses.PeliculaSerieResponse;
import com.example.demo.entities.PeliculaSerie;
import com.example.demo.exceptions.PageParametersInvalidException;
import com.example.demo.mappers.PeliculaSerieMapper;
import com.example.demo.repositories.PeliculaSerieRepository;
import com.example.demo.services.impl.PeliculaSerieServiceImpl;
import com.example.demo.validations.PeliculaSerieValidations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import utils.PeliculaSerieUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeliculaSerieServiceTest {

    @Mock
    private PeliculaSerieRepository peliculaSerieRepository;

    @Mock
    private PeliculaSerieValidations peliculaSerieValidations;

    @Mock
    private PeliculaSerieMapper peliculaSerieMapper;

    @InjectMocks
    private PeliculaSerieServiceImpl peliculaSerieService;

    @Test
    public void getAllPeliculaSeriesPaginadoOK() throws PageParametersInvalidException {

        Page<PeliculaSerie> peliculaSeriePage = PeliculaSerieUtils.getPeliculaSeriePage();
        List<PeliculaSerieResponse> peliculaSerieResponseList = PeliculaSerieUtils.getPeliculaSerieResponseList();

        when(peliculaSerieRepository.findAll(Mockito.any(Pageable.class))).thenReturn(peliculaSeriePage);
        when(peliculaSerieMapper.listToResponsePage(peliculaSeriePage)).thenReturn(peliculaSerieResponseList);

        List<PeliculaSerieResponse> listaPeliculaSeriesEncontrada = peliculaSerieService.getAllPeliculasSeriesPaginado(0, 1);

        assertThat(listaPeliculaSeriesEncontrada, hasSize(1));
    }

    @Test
    public void getAllPeliculaSeriesPaginadoPageExceptionKO() throws PageParametersInvalidException {

        assertThatThrownBy(() -> {
            peliculaSerieService.getAllPeliculasSeriesPaginado(-1, -1);
        }).isInstanceOf(PageParametersInvalidException.class);

        assertThatThrownBy(() -> {
            peliculaSerieService.getAllPeliculasSeriesPaginado(3, -1);
        }).isInstanceOf(PageParametersInvalidException.class);
    }

    @Test
    public void getPeliculaSerieByIdTest() {

        when(peliculaSerieValidations.validarCampoId(Mockito.any())).thenReturn(PeliculaSerieUtils.getPeliculaSerie());
        when(peliculaSerieMapper.toResponse(Mockito.any())).thenReturn(PeliculaSerieUtils.getPeliculaSerieResponse());

        PeliculaSerieResponse peliculaSerie = peliculaSerieService.getPeliculaSerieById(1L);

        assertThat(peliculaSerie, is(notNullValue()));
        assertThat(peliculaSerie.getDirector(), is(equalTo("test")));
        assertThat(peliculaSerie.getTitulo(), is(equalTo("test")));
    }

    @Test
    public void createPeliculaSerieTest() {

        when(peliculaSerieMapper.toResponse(Mockito.any())).thenReturn(PeliculaSerieUtils.getPeliculaSerieResponse());
        when(peliculaSerieMapper.requestToEntity(Mockito.any())).thenReturn(PeliculaSerieUtils.getPeliculaSerie());

        PeliculaSerieResponse peliculaSerie = peliculaSerieService.createPeliculaSerie(PeliculaSerieUtils.getPeliculaSerieRequest());

        assertThat(peliculaSerie, is(notNullValue()));
        assertThat(peliculaSerie.getDirector(), is(equalTo("test")));
        assertThat(peliculaSerie.getTitulo(), is(equalTo("test")));
    }

    @Test
    public void updatePeliculaSerieTest() {

        when(peliculaSerieMapper.toResponse(Mockito.any())).thenReturn(PeliculaSerieUtils.getPeliculaSerieResponse());
        when(peliculaSerieMapper.requestToEntity(Mockito.any())).thenReturn(PeliculaSerieUtils.getPeliculaSerie());
        when(peliculaSerieRepository.save(Mockito.any())).thenReturn(PeliculaSerieUtils.getPeliculaSerie());

        PeliculaSerieResponse peliculaSerie = peliculaSerieService.updatePeliculaSerie(1L, PeliculaSerieUtils.getPeliculaSerieRequest());

        assertThat(peliculaSerie.getDirector(), is(equalTo("test")));
        assertThat(peliculaSerie.getTitulo(), is(equalTo("test")));
    }

    @Test
    public void deletePeliculaSerieTest() {

        when(peliculaSerieValidations.validarCampoId(Mockito.any())).thenReturn(PeliculaSerieUtils.getPeliculaSerie());

        assertThatNoException().isThrownBy(() -> {
            peliculaSerieService.deletePeliculaSerie(1L);
        });
    }
}