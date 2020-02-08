package com.cardoso.controlemanutencao.api.services;

import java.util.List;
import java.util.Optional;

import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.entities.Equipamento;

public interface EquipamentoService {
	
	/**
	 * Retorna equipamento por marca
	 * 
	 * @param marca
	 * @return
	 */
	Optional<Equipamento> buscarEquipamentoPorMarca(String marca);
	
	/**
	 * Retorna equipamento por id
	 * 
	 * @param marca
	 * @return
	 */
	Optional<Equipamento> buscarEquipamentoPorId(Long id);
	
	
	/**
	 * Retorna equipamento por Cliente
	 * 
	 * @param cliente
	 * @return
	 */
	Optional<Equipamento> buscarEquipamentoPorCliente(Cliente cliente);
	
	
	/**
	 * Persistir equipamaneto na base de dado
	 * 
	 * @param equipamento
	 * @return
	 */
	Equipamento persistir(Equipamento equipamento);
	
	
	/**
	 * Remove equipamento
	 * 
	 * @param equipamento
	 */
	void remover(Long id);
	
	/**
	 * Lista todos os equipamentos
	 * 
	 * @return
	 */
	List<Equipamento> listar();
} 
