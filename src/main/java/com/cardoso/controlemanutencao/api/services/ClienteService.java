package com.cardoso.controlemanutencao.api.services;

import java.util.List;
import java.util.Optional;

import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.entities.Equipamento;

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
	 * Retorna um cliente passando o id
	 * 
	 * @param id
	 * @return
	 */
	Optional<Cliente> buscarClientePorId(Long id);
	
	/**
	 * Lista todos os Cliente
	 * 
	 * @return
	 */
	List<Cliente> listarCliente();
	
	/**
	 * Cadastra um novo Cliente na base de dados
	 * 
	 * @param Cliente
	 * @return
	 */
	
	Cliente persistir(Cliente cliente);
	
	/**
	 * Remove um cliente passando um id
	 * 
	 * @param id
	 */
	void remover(Long id);
	
	
	

}
