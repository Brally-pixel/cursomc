package com.jorgesoares.cursomc.repositories;

import com.jorgesoares.cursomc.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {


}
