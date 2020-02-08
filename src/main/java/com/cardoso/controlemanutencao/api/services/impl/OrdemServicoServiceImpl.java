package com.cardoso.controlemanutencao.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardoso.controlemanutencao.api.entities.OrdemServico;
import com.cardoso.controlemanutencao.api.repositories.OrdemServicoRepository;
import com.cardoso.controlemanutencao.api.services.OrdemServicoService;

@Service
public class OrdemServicoServiceImpl implements OrdemServicoService {
	
	private static final Logger log = LoggerFactory.getLogger(OrdemServicoServiceImpl.class);
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Override
	public List<OrdemServico> BuscarOSPorFuncionarioId(Long funcionarioId) {
		log.info("Buscando ordem de serviço para o funcionairo id: {}", funcionarioId);
		return this.ordemServicoRepository.findByFuncionarioId(funcionarioId);
	}

	@Override
	public List<OrdemServico> BuscarOSPorEquipamentoId(Long equipamentoId) {
		log.info("Buscando ordem de serviço para o equipamento id: {}", equipamentoId);
		return this.ordemServicoRepository.findByEquipamentoId(equipamentoId);
	}

	@Override
	public Optional<OrdemServico> buscarOrdemServicoPorId(Long id) {
		log.info("Buscando ordem de serviço para o id: {}", id);
		return this.ordemServicoRepository.findById(id);
	}
	
	@Override
	public OrdemServico persistir(OrdemServico ordemServico) {
		log.info("Persistindo ordem de serviço  OrdemServico: {}", ordemServico);
		return this.ordemServicoRepository.save(ordemServico);
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo ordem de serviço  id: {}", id);
		this.ordemServicoRepository.deleteById(id);
		
	}

	@Override
	public List<OrdemServico> listar() {
		log.info("Listando ordem de serivo");
		return this.ordemServicoRepository.findAll();
	}


}
