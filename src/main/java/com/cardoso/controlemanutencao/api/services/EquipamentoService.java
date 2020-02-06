package com.cardoso.controlemanutencao.api.services;

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
	
	
	Equipamento persistir(Equipamento equipamento);
}
