package com.cardoso.controlemanutencao.api.services;

import java.util.List;
import java.util.Optional;

import com.cardoso.controlemanutencao.api.entities.Equipamento;
import com.cardoso.controlemanutencao.api.entities.OrdemServico;

public interface OrdemServicoService {
	
	/**
	 * Retorna OS por id do Funcionario
	 * 
	 * @param funcionarioId
	 * @return
	 */
	List<OrdemServico> BuscarOSPorFuncionarioId(Long funcionarioId);
	
	/**
	 * Retorna OS por id do Equipamento
	 * 
	 * @param equipamentoId
	 * @return
	 */
	List<OrdemServico> BuscarOSPorEquipamentoId(Long equipamentoId);
	
	/**
	 * Retorna OS por id
	 * 
	 * @param id
	 * @return
	 */
	Optional<OrdemServico> buscarOrdemServicoPorId(Long id);
	
	/**
	 * Persiste na base de dados  uma OS
	 * 
	 * @param ordemServico
	 * @return
	 */
	OrdemServico persistir(OrdemServico ordemServico);
	
	/**
	 * Remove OS da base de dados
	 * 
	 * @param id
	 */
	void remover(Long id);
	
	/**
	 * Listar Ordem Servico
	 * 
	 * @return
	 */
	List<OrdemServico> listar();

}
