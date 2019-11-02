package com.jorgesoares.cursomc.repositories;

import com.jorgesoares.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Transactional
    Cliente findByEmail(String email);


}
