package com.jorgesoares.cursomc.services;

import com.jorgesoares.cursomc.domain.Produto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProdutoService {

    Produto find(Integer id);

    Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction);

}
