package com.jorgesoares.cursomc.resources;

import com.jorgesoares.cursomc.dto.EmailDTO;
import com.jorgesoares.cursomc.security.JWTUtil;
import com.jorgesoares.cursomc.security.UserSS;
import com.jorgesoares.cursomc.services.AuthService;
import com.jorgesoares.cursomc.services.UserService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @PostMapping(value="/refresh_token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generationToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @ApiModelProperty("Esqueci minha senha")
    @PostMapping(value="/forgot", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> forgot(
            @RequestBody @Valid EmailDTO objDto) {

        service.sendNewPassword(objDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}
