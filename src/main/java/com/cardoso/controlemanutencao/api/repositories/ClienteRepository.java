package com.cardoso.controlemanutencao.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cardoso.controlemanutencao.api.entities.Cliente;

@Transactional(readOnly = true)
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Cliente findByEmail(String email);
	
	Cliente findByTelefone(String telefone);
	
	Cliente findByEmailOrTelefone(String email, String telefone);
	

}
