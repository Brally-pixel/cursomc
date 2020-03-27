package com.jorgesoares.cursomc.services;

import com.jorgesoares.cursomc.domain.Cidade;

import java.util.List;

public interface CidadeService {

    List<Cidade> findByEstado(Integer estadoId);

}
