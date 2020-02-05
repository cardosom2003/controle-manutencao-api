package com.cardoso.controlemanutencao.api.repositories;

import java.awt.print.Pageable;
import java.util.List;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cardoso.controlemanutencao.api.entities.OrdemServico;

@Transactional(readOnly = true)
@NamedQueries({
	@NamedQuery(name ="OrdemServicoRepository.findByFuncionarioId",
			query = "SELECT ordem FROM OrdemServico ordem WHERE ordem.funcionario.id = :funcionarioId"),
	
	@NamedQuery(name ="OrdemServicoRepository.findByEquipamentoId",
	query = "SELECT ordem FROM OrdemServico ordem WHERE ordem.equipamento.id = :equipamentoId"),
	
	@NamedQuery(name ="OrdemServicoRepository.findByFuncionarioIdAndStatus",
	query = "SELECT ordem FROM OrdemServico ordem WHERE ordem.funcionario.id = :funcionarioId AND ordem.ordemStatus = :ordemStatus")
})
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long>{
	
	List<OrdemServico> findByFuncionarioId(@Param("FuncionarioId") Long funcionarioId);
	
//	Page<OrdemServico> findByFuncionarioId(@Param("FuncionarioId") Long funcionarioId, Pageable pageable);
	
	
	List<OrdemServico> findByEquipamentoId(@Param("EquipamentoId") Long equipamentoId);
	
//	Page<OrdemServico> findByEquipamentoId(@Param("EquipamentoId") Long equipamentoId, Pageable pageable);
	
	
//	List<OrdemServico> findByFuncionarioIdAndStatus(@Param("FuncionarioId") Long funcionarioId, @Param("ordemStatus") String ordemStatus);

}
