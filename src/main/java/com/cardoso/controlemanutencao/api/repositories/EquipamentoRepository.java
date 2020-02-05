package com.cardoso.controlemanutencao.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.entities.Equipamento;


@Transactional(readOnly = true)
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
	
	Equipamento findByMarca(String marca);
	
	Optional<Equipamento> findById(Long id);
	
	Equipamento findByCliente(Cliente cliente);

}
