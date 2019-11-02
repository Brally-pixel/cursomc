package com.jorgesoares.cursomc.dto;

import com.jorgesoares.cursomc.domain.Cliente;
import com.jorgesoares.cursomc.service.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotEmpty(message = "Prenchimento obrigatorio!")
    @Length(min = 3, max = 120, message = "O tamanho deve ser maior que 3 caracteres")
    private String nome;

    @NotEmpty(message = "Prenchimento obrigatorio!")
    @Email(message = "E-mail invalido!")
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente obj) {
        id = obj.getId();
        nome = obj.getNome();
        email = obj.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
