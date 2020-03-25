package com.jorgesoares.cursomc.resources;

import com.jorgesoares.cursomc.domain.Cliente;
import com.jorgesoares.cursomc.domain.enums.TipoCliente;
import com.jorgesoares.cursomc.dto.ClienteDTO;
import com.jorgesoares.cursomc.dto.ClienteNewDTO;
import com.jorgesoares.cursomc.services.impl.ClienteServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClienteResourceTest {
    @Mock
    ClienteServiceImpl service;

    @InjectMocks
    ClienteResource clienteResource;

    @Autowired
    private MockServletContext servletContext;

    private MockMvc mvc;

    @Mock
    protected HttpHeaders httpHeaders;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
        final GenericWebApplicationContext context = new GenericWebApplicationContext(servletContext);
        context.refresh();
        mvc = MockMvcBuilders.standaloneSetup(service).build();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testFind() throws Exception {
        Cliente response = new Cliente(1, "Maria Silva", "jhorge.junior@gmail.com", "98449628032",
                TipoCliente.PESSOAFISICA, "123");

        String url = String.format("/clientes?Id=%s", response.getId());

        when(service.find(anyInt())).thenReturn(response);

        mvc.perform(MockMvcRequestBuilders.get(url).headers(httpHeaders)
                .accept(MediaType.APPLICATION_JSON))
               // .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testInsert() throws Exception {

        String url = String.format("/clientes");

        when(service.fromDTO(any(ClienteNewDTO.class))).thenReturn(new Cliente(0, null, null, null, TipoCliente.PESSOAFISICA, null));

        when(service.insert(any(Cliente.class))).thenReturn(new Cliente(0, null, null, null, TipoCliente.PESSOAFISICA, null));

        mvc.perform(MockMvcRequestBuilders.get(url).headers(httpHeaders)
                .accept(MediaType.APPLICATION_JSON))
                // .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() {
        when(service.update(any())).thenReturn(new Cliente(Integer.valueOf(0), null, null, null, TipoCliente.PESSOAFISICA, null));
        //when(service.fromDTO(any())).thenReturn(new Cliente(Integer.valueOf(0), null, null, null, TipoCliente.PESSOAFISICA, null));

        ResponseEntity<Void> result = clienteResource.update(new ClienteDTO(new Cliente(Integer.valueOf(0), null, null, null, TipoCliente.PESSOAFISICA, null)), Integer.valueOf(0));
        Assert.assertEquals(result, null);
    }

    @Test
    public void testDelete() {
        ResponseEntity<Void> result = clienteResource.delete(Integer.valueOf(0));
        Assert.assertEquals(result, null);
    }

    @Test
    public void testFindAll() {
        when(service.findAll()).thenReturn(Arrays.<Cliente>asList(new Cliente(null, null, null, null, TipoCliente.PESSOAFISICA, null)));

        ResponseEntity<List<ClienteDTO>> result = clienteResource.findAll();
        Assert.assertEquals(result, null);
    }

    @Test
    public void testFindPage() {
        when(service.findPage(anyInt(), anyInt(), anyString(), anyString())).thenReturn(null);

        ResponseEntity<Page<ClienteDTO>> result = clienteResource.findPage(Integer.valueOf(0), Integer.valueOf(0), "orderBy", "direction");
        Assert.assertEquals(result, null);
    }

    @Test
    public void testUploadProfilePicture() {
        when(service.uploadProfilePicture(any())).thenReturn(null);

        ResponseEntity<Void> result = clienteResource.uploadProfilePicture(null);
        Assert.assertEquals(result, null);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme