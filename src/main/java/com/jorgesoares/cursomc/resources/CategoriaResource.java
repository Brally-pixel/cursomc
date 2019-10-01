package com.jorgesoares.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import com.jorgesoares.cursomc.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jorgesoares.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

    @RequestMapping(value = "/{id}" , method = RequestMethod.GET)
    public ResponseEntity<?> find(

    		@PathVariable Integer id){

    	Categoria obj = service.find(id);
    	return ResponseEntity.ok().body(obj);


    }

}
