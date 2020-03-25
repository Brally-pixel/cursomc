package com.jorgesoares.cursomc.services.impl;

import com.jorgesoares.cursomc.domain.Cliente;
import com.jorgesoares.cursomc.repositories.ClienteRepository;
import com.jorgesoares.cursomc.services.AuthService;
import com.jorgesoares.cursomc.services.EmailService;
import com.jorgesoares.cursomc.services.exceptions.ObjNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    @Override
    public void sendNewPassword(String email){
        Cliente cliente = repository.findByEmail(email);

        if (cliente == null){
            throw new ObjNotFoundException("Email nao encontrado");
        }
        String newPass = newPassword();
        cliente.setSenha(passwordEncoder.encode(newPass));

        repository.save(cliente);
        emailService.sendNewPasswordEmail(cliente, newPass);
    }
    @Override
    public String newPassword(){
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    @Override
    public char randomChar(){
        int opt = rand.nextInt(3);
        if(opt == 0){ //gera um digito
            return (char) (rand.nextInt(10) + 48);
        }else if (opt == 1){ // gera letra maiscula
            return (char) (rand.nextInt(26) + 65);
        }else { //letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }

}
