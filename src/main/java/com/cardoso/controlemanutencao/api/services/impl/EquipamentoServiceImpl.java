package com.cardoso.controlemanutencao.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.entities.Equipamento;
import com.cardoso.controlemanutencao.api.repositories.EquipamentoRepository;
import com.cardoso.controlemanutencao.api.services.EquipamentoService;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {
	
	private static final Logger log = LoggerFactory.getLogger(EquipamentoServiceImpl.class);
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;

	@Override
	public Optional<Equipamento> buscarEquipamentoPorMarca(String marca) {
		log.info("Buscando equipamento por marca: {}", marca);
		return Optional.ofNullable( this.equipamentoRepository.findByMarca(marca));
		
	}

	@Override
	public Optional <Equipamento> buscarEquipamentoPorId(Long id) {
		log.info("Buscando equipamento por id: {}", id);
		return this.equipamentoRepository.findById(id);
		 
	}

	@Override
	public Optional<Equipamento> buscarEquipamentoPorCliente(Cliente cliente) {
		log.info("Buscando equipamento por Cliente: {}", cliente);
		return Optional.ofNullable(this.equipamentoRepository.findByCliente(cliente));
	}

	@Override
	public Equipamento persistir(Equipamento equipamento) {
		log.info("Persistindo equipamento: {}", equipamento);
		return this.equipamentoRepository.save(equipamento);
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo equipamento id: ", id);
		this.equipamentoRepository.deleteById(id);
	}

	@Override
	public List<Equipamento> listar() {
		log.info("Listando equipamentos:");
		return this.equipamentoRepository.findAll();
	}

}
