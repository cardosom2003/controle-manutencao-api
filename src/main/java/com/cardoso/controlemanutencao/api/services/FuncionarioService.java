package com.cardoso.controlemanutencao.api.services;

import java.util.List;
import java.util.Optional;

import com.cardoso.controlemanutencao.api.entities.Funcionario;

public interface FuncionarioService {
	
	
	/**
	 * Retorna um Funcionario passando o nome
	 * 
	 * @param nome
	 * @return
	 */
	Optional<Funcionario> buscarFuncionarioPorNome(String nome);
	
	/**
	 * Retorna um funcionario passando o email
	 * 
	 * @param email
	 * @return
	 */
	Optional<Funcionario> buscarPorEmail(String email);

	/**
	 * Retorna um  Funcionario passando o id
	 * 
	 * @param id
	 * @return
	 */
	Optional<Funcionario> buscarFuncionarioPorId(Long id);
	
	/**
	 * Sava um Funcionario na Base de dados
	 * 
	 * @param funcionario
	 * @return
	 */
	Funcionario persistir(Funcionario funcionario);
	
	/**
	 * Remove  Funcionario
	 * 
	 * @param id
	 */
	void remove(Long id);
	
	/**
	 * Lista funcionarios
	 * 
	 * @return
	 */
	List<Funcionario> listar();

}
