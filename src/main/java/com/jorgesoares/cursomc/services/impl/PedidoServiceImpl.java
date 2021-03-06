package com.jorgesoares.cursomc.services.impl;

import com.jorgesoares.cursomc.domain.Cliente;
import com.jorgesoares.cursomc.domain.ItemPedido;
import com.jorgesoares.cursomc.domain.PagamentoComBoleto;
import com.jorgesoares.cursomc.domain.Pedido;
import com.jorgesoares.cursomc.domain.enums.EstadoPagamento;
import com.jorgesoares.cursomc.repositories.ItemPedidoRepository;
import com.jorgesoares.cursomc.repositories.PagamentoRepository;
import com.jorgesoares.cursomc.repositories.PedidoRepository;
import com.jorgesoares.cursomc.repositories.ProdutoRepository;
import com.jorgesoares.cursomc.security.UserSS;
import com.jorgesoares.cursomc.services.*;
import com.jorgesoares.cursomc.services.exceptions.AuthorizationException;
import com.jorgesoares.cursomc.services.exceptions.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    @Override
    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    @Override
    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()){
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }

    @Override
    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {

        UserSS user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.find(user.getId());
        return repo.findByCliente(cliente, pageRequest);

    }
}
