package com.cardoso.controlemanutencao.api.services.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.repositories.ClienteRepository;
import com.cardoso.controlemanutencao.api.services.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@Autowired
	private ClienteRepository clienteRepository;
	

	@Override
	public Optional<Cliente> buscarPorClienteEmail(String email) {
		log.info("Buscando cliente para o e-mail: {}", email);
		return Optional.ofNullable(clienteRepository.findByEmail(email));
	}

	@Override
	public Optional<Cliente> buscarClientePorTelefone(String telefone) {
		log.info("Buscando cliente para o telefone: {}", telefone);
		return Optional.ofNullable(clienteRepository.findByTelefone(telefone));
	}

	
	@Override
	public Optional<Cliente> buscarClientePorEmailOrTelefone(String email, String telefone) {
		log.info("Buscando cliente para o emai:l {} ou telefone: {}", email, telefone);
		return Optional.ofNullable(clienteRepository.findByEmailOrTelefone(email, telefone));
		
	}
	
	@Override
	public Cliente persistir(Cliente Cliente) {
		log.info("Persistindo cliente: {}", Cliente);
		return this.clienteRepository.save(Cliente);
	}


}
