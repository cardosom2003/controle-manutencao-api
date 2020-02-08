package com.cardoso.controlemanutencao.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.entities.Equipamento;


@Transactional(readOnly = true)
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
	
	Equipamento findByMarca(String marca);
	
	Equipamento findByCliente(Cliente cliente);

}
