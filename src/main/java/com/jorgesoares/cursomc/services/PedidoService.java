package com.jorgesoares.cursomc.services;

import com.jorgesoares.cursomc.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PedidoService {

    Pedido find(Integer id);

    Pedido insert(Pedido obj);

    Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);
}
