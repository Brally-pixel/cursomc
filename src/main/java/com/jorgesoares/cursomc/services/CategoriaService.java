package com.jorgesoares.cursomc.services;

import com.jorgesoares.cursomc.domain.Categoria;
import com.jorgesoares.cursomc.dto.CategoriaDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CategoriaService {

    Categoria find(Integer id);

    Categoria insert(Categoria obj);

    Categoria update(Categoria obj);

    void updateData(Categoria newObj, Categoria obj);

    void delete(Integer id);

    List<Categoria> findAll();

    Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

    Categoria fromDTO(CategoriaDTO objDto);

}
