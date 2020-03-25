package com.jorgesoares.cursomc.services.impl;

import com.jorgesoares.cursomc.domain.Cidade;
import com.jorgesoares.cursomc.repositories.CidadeRepository;
import com.jorgesoares.cursomc.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeServiceImpl implements CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Override
    public List<Cidade> find(Integer estadoId) {
        return cidadeRepository.findCidade(estadoId);
    }
}
