package com.jorgesoares.cursomc.services.validation;

import com.jorgesoares.cursomc.domain.Cliente;
import com.jorgesoares.cursomc.domain.enums.TipoCliente;
import com.jorgesoares.cursomc.dto.ClienteNewDTO;
import com.jorgesoares.cursomc.repositories.ClienteRepository;
import com.jorgesoares.cursomc.resources.exception.FieldMessage;
import com.jorgesoares.cursomc.services.validation.util.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        // inclua os testes aqui, inserindo erros na lista

        if (objDto.getTipo() == null) {
            list.add(new FieldMessage("tipo", "Tipo nao pode ser nulo"));
        }
        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfCnpj())) {
            list.add(new FieldMessage("CPF", "CPF nao é valido!"));
        }

        if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfCnpj())) {
            list.add(new FieldMessage("CPF", "CNPJ nao é valido!"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if (aux != null) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }

}
