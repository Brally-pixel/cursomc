package com.jorgesoares.cursomc.services;

import com.jorgesoares.cursomc.domain.PagamentoComBoleto;

import java.util.Date;


public interface BoletoService {

    void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido);
}
