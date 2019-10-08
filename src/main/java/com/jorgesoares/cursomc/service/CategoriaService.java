package com.jorgesoares.cursomc.service;

import com.jorgesoares.cursomc.domain.Categoria;
import com.jorgesoares.cursomc.repositories.CategoriaRepository;
import com.jorgesoares.cursomc.service.exceptions.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }
    public Categoria insert(Categoria obj){
        obj.setId(null);
        return repo.save(obj);

    }

    public Categoria update(Categoria obj){
        find(obj.getId());
        return repo.save(obj);
    }
}
