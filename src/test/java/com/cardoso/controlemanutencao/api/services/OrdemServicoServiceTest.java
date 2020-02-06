package com.cardoso.controlemanutencao.api.services;

import java.util.List;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cardoso.controlemanutencao.api.entities.OrdemServico;
import com.cardoso.controlemanutencao.api.repositories.OrdemServicoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OrdemServicoServiceTest {
	
	@MockBean
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Before
	public void setUp() throws Exception{
		BDDMockito.given(this.ordemServicoRepository.findByFuncionarioId(Mockito.anyLong()))
		.willReturn(new ArrayList<OrdemServico>());
		BDDMockito.given(this.ordemServicoRepository.findByEquipamentoId(Mockito.anyLong()))
		.willReturn(new ArrayList<OrdemServico>());
	//	BDDMockito.given(this.ordemServicoRepository.findById(Mockito.anyLong())).willReturn(new OrdemServico());
		BDDMockito.given(this.ordemServicoRepository.save(Mockito.any(OrdemServico.class))).willReturn(new OrdemServico());
	}
	
	@Test
	public void testBuscarOrdemServicoPorFuncionarioId(){
		
		List<OrdemServico> ordemServico = this.ordemServicoService.BuscarOSPorFuncionarioId(1L);
		assertNotNull(ordemServico);
	}
	
	@Test
	public void testBuscarOrdemServicoPorEquipamentoId(){
		
		List<OrdemServico> ordemServico = this.ordemServicoService.BuscarOSPorEquipamentoId(1L);
		assertNotNull(ordemServico);
	}
	
	@Test
	public void testPersistirOrdemServico() {
		OrdemServico ordemServico = this.ordemServicoService.persistir(new OrdemServico());
		assertNotNull(ordemServico);
	}

}
