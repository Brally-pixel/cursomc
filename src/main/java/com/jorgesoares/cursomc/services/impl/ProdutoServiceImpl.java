package com.jorgesoares.cursomc.services.impl;

import com.jorgesoares.cursomc.domain.Categoria;
import com.jorgesoares.cursomc.domain.Produto;
import com.jorgesoares.cursomc.repositories.CategoriaRepository;
import com.jorgesoares.cursomc.repositories.ProdutoRepository;
import com.jorgesoares.cursomc.services.ProdutoService;
import com.jorgesoares.cursomc.services.exceptions.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Produto find(Integer id) {
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    @Override
    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);

    }
}
