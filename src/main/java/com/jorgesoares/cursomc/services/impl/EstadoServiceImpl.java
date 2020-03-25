package com.jorgesoares.cursomc.services.impl;

import com.jorgesoares.cursomc.domain.Estado;
import com.jorgesoares.cursomc.repositories.EstadoRepository;
import com.jorgesoares.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoServiceImpl implements EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public List<Estado> findAll() {
        return estadoRepository.findAllByOrderByNome();
    }
}
