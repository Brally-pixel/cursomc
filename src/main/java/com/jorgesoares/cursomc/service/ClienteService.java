package com.jorgesoares.cursomc.service;

import com.jorgesoares.cursomc.domain.Categoria;
import com.jorgesoares.cursomc.domain.Cliente;
import com.jorgesoares.cursomc.repositories.CategoriaRepository;
import com.jorgesoares.cursomc.repositories.ClienteRepository;
import com.jorgesoares.cursomc.service.exceptions.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id) {
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
}
