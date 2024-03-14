package com.example.demo.services.impl;

import com.example.demo.dtos.requests.PeliculaSerieRequest;
import com.example.demo.dtos.responses.PeliculaSerieResponse;
import com.example.demo.entities.PeliculaSerie;
import com.example.demo.exceptions.PageParametersInvalidException;
import com.example.demo.mappers.PeliculaSerieMapper;
import com.example.demo.repositories.PeliculaSerieRepository;
import com.example.demo.services.PeliculaSerieService;
import com.example.demo.validations.PeliculaSerieValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class PeliculaSerieServiceImpl implements PeliculaSerieService {

    private final PeliculaSerieRepository peliculaSerieRepository;
    private final PeliculaSerieValidations peliculaSerieValidations;
    private final PeliculaSerieMapper peliculaSerieMapper;

    @Cacheable("peliculasSeries")
    @Override
    public List<PeliculaSerieResponse> getAllPeliculasSeriesPaginado(int pagina, int tamanio) throws PageParametersInvalidException {
        if (pagina < 0) {
            throw new PageParametersInvalidException("El parámetro pagina no puede ser menor que 0");
        } else if (tamanio < 1) {
            throw new PageParametersInvalidException("El parámetro tamanio no puede ser menor que 1");
        }

        PageRequest pageRequest = PageRequest.of(pagina, tamanio);
        Page<PeliculaSerie> peliculasSeriesResult = peliculaSerieRepository.findAll(pageRequest);
        log.info("Consultando películas y series por paginación {}", peliculasSeriesResult);
        return peliculaSerieMapper.listToResponsePage(peliculasSeriesResult);
    }

    @Override
    public List<PeliculaSerieResponse> getAllPeliculas() {
        List<PeliculaSerie> peliculasList = peliculaSerieRepository.findByTemporadasIsNull();
        log.info("Consultando TODAS las películas {}", peliculasList);
        return peliculaSerieMapper.listToResponse(peliculasList);
    }

    @Override
    public List<PeliculaSerieResponse> getAllSeries() {
        List<PeliculaSerie> seriesList = peliculaSerieRepository.findByDuracionIsNull();
        log.info("Consultando TODAS las series {}", seriesList);
        return peliculaSerieMapper.listToResponse(seriesList);
    }

    @Cacheable("peliculasSeries")
    @Override
    public PeliculaSerieResponse getPeliculaSerieById(Long id) {
        PeliculaSerie peliculaSerieEntity = peliculaSerieValidations.validarCampoId(id);
        log.info("Consultando película o serie por id {}", peliculaSerieEntity.getId());
        return peliculaSerieMapper.toResponse(peliculaSerieEntity);
    }

    @CacheEvict(cacheNames ="peliculasSeries", allEntries = true)
    @Override
    public PeliculaSerieResponse createPeliculaSerie(PeliculaSerieRequest peliculaSerieRequest) {
        peliculaSerieValidations.validarCamposObligatorios(peliculaSerieRequest);
        PeliculaSerie peliculaSerieResult = peliculaSerieRepository.save(peliculaSerieMapper.requestToEntity(peliculaSerieRequest));
        log.info("Se ha guardado la pelicula o serie {}", peliculaSerieResult);
        return peliculaSerieMapper.toResponse(peliculaSerieResult);
    }

    @CacheEvict(cacheNames ="peliculasSeries", allEntries = true)
    @Override
    public PeliculaSerieResponse updatePeliculaSerie(Long id, PeliculaSerieRequest peliculaSerieRequest) {
        peliculaSerieValidations.validarCampoId(id);
        peliculaSerieValidations.validarCamposObligatorios(peliculaSerieRequest);
        PeliculaSerie peliculaSerie = peliculaSerieMapper.requestToEntity(peliculaSerieRequest);
        peliculaSerie.setId(id);
        PeliculaSerie puntuacionResult = peliculaSerieRepository.save(peliculaSerie);
        log.info("Modificando película o serie con id {}", puntuacionResult.getId());
        return peliculaSerieMapper.toResponse(puntuacionResult);
    }

    @CacheEvict(cacheNames ="peliculasSeries", allEntries = true)
    @Override
    public void deletePeliculaSerie(Long id) {
        peliculaSerieValidations.validarCampoId(id);
        peliculaSerieRepository.deleteById(id);
        log.info("Eliminando película o serie con id {}", id);
    }
}
