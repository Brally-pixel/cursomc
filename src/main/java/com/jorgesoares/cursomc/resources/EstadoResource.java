package com.jorgesoares.cursomc.resources;

import com.jorgesoares.cursomc.domain.Cidade;
import com.jorgesoares.cursomc.domain.Estado;
import com.jorgesoares.cursomc.dto.CidadeDTO;
import com.jorgesoares.cursomc.dto.EstadoDTO;
import com.jorgesoares.cursomc.services.CidadeService;
import com.jorgesoares.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<EstadoDTO>> findAll(){
        List<Estado> objList = service.findAll();
        List<EstadoDTO> estadoDTOList = objList.stream().map(EstadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(estadoDTOList);
    }

    @GetMapping(value = "/{estado_id}/cidades", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CidadeDTO>> findCidades(

            @PathVariable Integer estadoId){
        List<Cidade> list = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> cidadeDTOList = list.stream().map(CidadeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidadeDTOList);
    }
}
