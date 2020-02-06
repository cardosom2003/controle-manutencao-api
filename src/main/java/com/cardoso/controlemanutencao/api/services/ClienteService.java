package com.cardoso.controlemanutencao.api.services;

import java.util.Optional;

import com.cardoso.controlemanutencao.api.entities.Cliente;

public interface ClienteService {
	
	/**
	 * Retorna um Cliente dado um e-mail
	 * 
	 * @param email
	 * @return Optional<Cliente>
	 */
	Optional<Cliente> buscarPorClienteEmail(String email);
	
	/**
	 * Retorna um Cliente passando um telefone
	 * 
	 * @param telefone
	 * @return
	 */
	Optional<Cliente> buscarClientePorTelefone(String telefone);
	
	/**
	 * Retorna um Cliene passando  telefone ou email
	 * 
	 * @param email
	 * @param telefone
	 * @return
	 */
	Optional<Cliente> buscarClientePorEmailOrTelefone(String email, String telefone);
	/**
	 * Cadastra um novo Cliente na base de dados
	 * 
	 * @param Cliente
	 * @return
	 */
	
	Cliente persistir(Cliente cliente);
	
	
	

}
