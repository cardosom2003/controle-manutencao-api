package com.cardoso.controlemanutencao.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardoso.controlemanutencao.api.entities.Funcionario;
import com.cardoso.controlemanutencao.api.repositories.FuncionarioRepository;
import com.cardoso.controlemanutencao.api.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Optional<Funcionario> buscarFuncionarioPorNome(String nome) {
		log.info("Buscando funcionario para o nome: {}", nome);
		return Optional.ofNullable(funcionarioRepository.findByNome(nome));
		
	}

	@Override
	public Optional<Funcionario> buscarFuncionarioPorId(Long id) {
		log.info("Buscando funcionario para o id: {}", id);
		return this.funcionarioRepository.findById(id);
		  
	}

	@Override
	public Funcionario persistir(Funcionario funcionario) {
		log.info("Persistindo funcionario : {}", funcionario);
		return this.funcionarioRepository.save(funcionario);
	}

}
