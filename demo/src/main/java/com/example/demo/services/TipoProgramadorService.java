package com.example.demo.services;

import com.example.demo.dtos.requests.TipoProgramadorRequest;
import com.example.demo.dtos.responses.TipoProgramadorResponse;

import java.util.List;

public interface TipoProgramadorService {

    List<TipoProgramadorResponse> getAll();
    TipoProgramadorResponse getTipoProgramadorById(Long id);
    TipoProgramadorResponse createTipoProgramador(TipoProgramadorRequest tipoProgramador);
    TipoProgramadorResponse updateTipoProgramador(Long id, TipoProgramadorRequest tipoProgramador);
    void deleteTipoProgramador(Long id);
}
