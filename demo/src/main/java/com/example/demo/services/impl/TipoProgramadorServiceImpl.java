package com.example.demo.services.impl;

import com.example.demo.dtos.requests.TipoProgramadorRequest;
import com.example.demo.dtos.responses.TipoProgramadorResponse;
import com.example.demo.entities.TipoProgramador;
import com.example.demo.mappers.TipoProgramadorMapper;
import com.example.demo.repositories.TipoProgramadorRepository;
import com.example.demo.services.TipoProgramadorService;
import com.example.demo.validations.TipoProgramadorValidations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class TipoProgramadorServiceImpl implements TipoProgramadorService {

    private final TipoProgramadorRepository tipoProgramadorRepository;
    private final TipoProgramadorValidations tipoProgramadorValidations;
    private final TipoProgramadorMapper tipoProgramadorMapper;

    @Cacheable("tiposProgramador")
    @Override
    public List<TipoProgramadorResponse> getAll(){
        List<TipoProgramador> TipoProgramadoresResult = tipoProgramadorRepository.findAll();
        log.info("Consultando todos los TipoProgramadores {}", TipoProgramadoresResult);
        return tipoProgramadorMapper.listToResponse(TipoProgramadoresResult);
    }

    @Cacheable("tiposProgramador")
    @Override
    public TipoProgramadorResponse getTipoProgramadorById(Long id) {
        TipoProgramador TipoProgramadorEntity = tipoProgramadorValidations.validarCampoId(id);
        log.info("Consultando TipoProgramadores por id {}", TipoProgramadorEntity.getId());
        return tipoProgramadorMapper.toResponse(TipoProgramadorEntity);
    }

    @CacheEvict(cacheNames ="tiposProgramador", allEntries = true)
    @Override
    public TipoProgramadorResponse createTipoProgramador(TipoProgramadorRequest tipoProgramadorRequest) {
        tipoProgramadorValidations.validarCamposObligatorios(tipoProgramadorRequest);
        TipoProgramador TipoProgramador = tipoProgramadorMapper.requestToEntity(tipoProgramadorRequest);
        TipoProgramador TipoProgramadorResult = tipoProgramadorRepository.save(TipoProgramador);
        log.info("Se ha guardado el tipoProgramador {}", TipoProgramadorResult);
        return tipoProgramadorMapper.toResponse(TipoProgramadorResult);
    }

    @CacheEvict(cacheNames ="tiposProgramador", allEntries = true)
    @Override
    public TipoProgramadorResponse updateTipoProgramador(Long id, TipoProgramadorRequest TipoProgramadorRequest) {
        tipoProgramadorValidations.validarCampoId(id);
        tipoProgramadorValidations.validarCamposObligatorios(TipoProgramadorRequest);
        TipoProgramador TipoProgramador = tipoProgramadorMapper.requestToEntity(TipoProgramadorRequest);
        TipoProgramador.setId(id);
        TipoProgramador TipoProgramadorResult = tipoProgramadorRepository.save(TipoProgramador);
        log.info("Modificando tipoProgramador con id {} " + id, TipoProgramadorResult);
        return tipoProgramadorMapper.toResponse(TipoProgramadorResult);
    }

    @CacheEvict(cacheNames ="tiposProgramador", allEntries = true)
    @Override
    public void deleteTipoProgramador(Long id) {
        tipoProgramadorValidations.validarCampoId(id);
        tipoProgramadorRepository.deleteById(id);
        log.info("Eliminando tipoProgramador con id {}", id);
    }
}
