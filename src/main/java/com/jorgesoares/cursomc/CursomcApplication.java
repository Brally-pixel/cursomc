package com.jorgesoares.cursomc;

import com.jorgesoares.cursomc.domain.Categoria;
import com.jorgesoares.cursomc.domain.Cidade;
import com.jorgesoares.cursomc.domain.Cliente;
import com.jorgesoares.cursomc.domain.Endereco;
import com.jorgesoares.cursomc.domain.Estado;
import com.jorgesoares.cursomc.domain.ItemPedido;
import com.jorgesoares.cursomc.domain.Pagamento;
import com.jorgesoares.cursomc.domain.PagamentoComBoleto;
import com.jorgesoares.cursomc.domain.PagamentoComCartao;
import com.jorgesoares.cursomc.domain.Pedido;
import com.jorgesoares.cursomc.domain.Produto;
import com.jorgesoares.cursomc.domain.enums.EstadoPagamento;
import com.jorgesoares.cursomc.domain.enums.TipoCliente;
import com.jorgesoares.cursomc.repositories.CategoriaRepository;
import com.jorgesoares.cursomc.repositories.CidadeRepository;
import com.jorgesoares.cursomc.repositories.ClienteRepository;
import com.jorgesoares.cursomc.repositories.EnderecoRepository;
import com.jorgesoares.cursomc.repositories.EstadoRepository;
import com.jorgesoares.cursomc.repositories.ItemPedidoRepository;
import com.jorgesoares.cursomc.repositories.PagamentoRepository;
import com.jorgesoares.cursomc.repositories.PedidoRepository;
import com.jorgesoares.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;


	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		Categoria cat8 = new Categoria(null, "Roupas");


		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de Escritorio", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV", 1200.00);
		Produto p8 = new Produto(null, "Rocadeira", 800.00);
		Produto p9 = new Produto(null, "abajour", 100.00);
		Produto p10 = new Produto(null, "Pente", 10.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		Produto p12 = new Produto(null, "Condicionador", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4));
		cat3.getProdutos().addAll(Arrays.asList(p5,p6));
		cat4.getProdutos().addAll(Arrays.asList(p1,p2,p3,p7));
		cat5.getProdutos().addAll(Collections.singletonList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9,p10));
		cat7.getProdutos().addAll(Collections.singletonList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Collections.singletonList(cat2));
		p5.getCategorias().addAll(Collections.singletonList(cat3));
		p6.getCategorias().addAll(Collections.singletonList(cat3));
		p7.getCategorias().addAll(Collections.singletonList(cat4));
		p8.getCategorias().addAll(Collections.singletonList(cat5));
		p9.getCategorias().addAll(Collections.singletonList(cat6));
		p10.getCategorias().addAll(Collections.singletonList(cat6));
		p11.getCategorias().addAll(Collections.singletonList(cat7));


		Estado est1 = new Estado (null, "Minas Gerais");
		Estado est2 = new Estado (null, "São Paulo");

		Cidade c1 = new Cidade (null, "Uberlandia", est1);
		Cidade c2 = new Cidade (null, "São Paulo", est2);
		Cidade c3 = new Cidade (null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva","maria@gmail.com","123456", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("14145151", "1340975097"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300","Apto 303", "Jardim", "24252523",cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida matos", "105","Casa", "Jardim", "24252523",cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Collections.singletonList(ip3));

		p1.getItens().addAll(Collections.singletonList(ip1));
		p2.getItens().addAll(Collections.singletonList(ip3));
		p3.getItens().addAll(Collections.singletonList(ip2));


		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8));
		produtoRepository.saveAll(Arrays.asList(p1,p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2,c3));
		clienteRepository.saveAll(Collections.singletonList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));



	}
}
