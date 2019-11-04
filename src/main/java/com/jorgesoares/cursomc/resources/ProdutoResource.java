package com.jorgesoares.cursomc.resources;

import com.jorgesoares.cursomc.domain.Categoria;
import com.jorgesoares.cursomc.domain.Produto;
import com.jorgesoares.cursomc.dto.CategoriaDTO;
import com.jorgesoares.cursomc.dto.ProdutoDTO;
import com.jorgesoares.cursomc.resources.utils.URL;
import com.jorgesoares.cursomc.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public ResponseEntity<?> find(

    		@PathVariable Integer id){

    	Produto obj = service.find(id);
    	return ResponseEntity.ok().body(obj);


    }

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction){
    	String nomeDecod = URL.decodeParam(nome);
    	List<Integer> ids = URL.decodeIntList(categorias);

		Page<Produto> list = service.search(nomeDecod, ids, page, linesPerPage, orderBy, direction);

		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}

}
