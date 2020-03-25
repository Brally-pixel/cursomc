package com.jorgesoares.cursomc.services;

import com.jorgesoares.cursomc.domain.Cliente;
import com.jorgesoares.cursomc.dto.ClienteDTO;
import com.jorgesoares.cursomc.dto.ClienteNewDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;


public interface ClienteService {

    Cliente find(Integer id);

    Cliente insert(Cliente obj);

    Cliente update(Cliente obj);

    void updateData(Cliente newObj, Cliente obj);

    void delete(Integer id);

    List<Cliente> findAll();

    Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

    Cliente fromDTO(ClienteDTO objDto);

    Cliente fromDTO(ClienteNewDTO objDto);

    URI uploadProfilePicture(MultipartFile multipartFile);
}
