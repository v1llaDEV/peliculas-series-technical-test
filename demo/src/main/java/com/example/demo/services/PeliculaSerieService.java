package com.example.demo.services;

import com.example.demo.dtos.requests.PeliculaSerieRequest;
import com.example.demo.dtos.responses.PeliculaSerieResponse;
import com.example.demo.exceptions.PageParametersInvalidException;

import java.util.List;

public interface PeliculaSerieService {

    List<PeliculaSerieResponse> getAllPeliculasSeriesPaginado(int pagina, int tamanio) throws PageParametersInvalidException;
    List<PeliculaSerieResponse> getAllPeliculas();
    List<PeliculaSerieResponse> getAllSeries();
    PeliculaSerieResponse getPeliculaSerieById(Long id);
    PeliculaSerieResponse createPeliculaSerie(PeliculaSerieRequest peliculaSerieRequest);
    PeliculaSerieResponse updatePeliculaSerie(Long id, PeliculaSerieRequest peliculaSerieRequest);
    void deletePeliculaSerie(Long id);
}
