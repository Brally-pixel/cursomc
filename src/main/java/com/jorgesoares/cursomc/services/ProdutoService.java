package com.jorgesoares.cursomc.services;

import com.jorgesoares.cursomc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProdutoService {

    Produto find(Integer id);

    Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction);

}
