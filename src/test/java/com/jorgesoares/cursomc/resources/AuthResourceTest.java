package com.jorgesoares.cursomc.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorgesoares.cursomc.dto.EmailDTO;
import com.jorgesoares.cursomc.security.JWTUtil;
import com.jorgesoares.cursomc.services.AuthService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthResourceTest {

    private static ObjectMapper mapper = new ObjectMapper();

    @Mock
    JWTUtil jwtUtil;

    @Mock
    AuthService service;

    @InjectMocks
    AuthResource authResource;

    @Autowired
    private MockServletContext servletContext;

    private MockMvc mvc;

    @Mock
    protected HttpHeaders httpHeaders;

    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
        final GenericWebApplicationContext context = new GenericWebApplicationContext(servletContext);
        context.refresh();
        mvc = MockMvcBuilders.standaloneSetup(authResource).build();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testForgot() throws Exception {

        EmailDTO request = new EmailDTO();
        request.setEmail("abc");

        String jsonType = "{\"email\":\"abc@gmail.com\"}";

        String url = String.format("/auth/forgot");

        mvc.perform(MockMvcRequestBuilders.post(url)
                .headers(httpHeaders)
                .content(jsonType)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testForgotWithBadRequest() throws Exception {

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail("");

        String url = String.format("/auth/forgot");

        mvc.perform(post(url)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
