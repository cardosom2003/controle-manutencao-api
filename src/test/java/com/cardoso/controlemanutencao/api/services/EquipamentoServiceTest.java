package com.cardoso.controlemanutencao.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

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

import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.entities.Equipamento;
import com.cardoso.controlemanutencao.api.repositories.EquipamentoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EquipamentoServiceTest {
	
	@MockBean
	private EquipamentoRepository equipamentoRepository;
	
	@Autowired
	private EquipamentoService equipamentoService;
	
	@Before
	public void setUp() throws Exception{
		
		BDDMockito.given(this.equipamentoRepository.findByMarca(Mockito.anyString())).willReturn(new Equipamento());
	//	Optional<Equipamento> equipamento = this.equipamentoRepository.findById(Mockito.anyLong());
		BDDMockito.given(this.equipamentoRepository.findByCliente(Mockito.any(Cliente.class))).willReturn(new Equipamento());
		BDDMockito.given(this.equipamentoRepository.save(Mockito.any(Equipamento.class))).willReturn(new Equipamento());
	}
	
	@Test
	public void testBuscarEquipamentoPorMarca() {
		Optional<Equipamento> equipamento = this.equipamentoService.buscarEquipamentoPorMarca("Sony");
		assertTrue(equipamento.isPresent());
	}
	
	@Test
	public void testBuscarEquipamentoPorCliente() {
		
		Optional<Equipamento> equipamento = this.equipamentoService.buscarEquipamentoPorCliente(new Cliente());
		assertTrue(equipamento.isPresent());
	}
	
	public void testPersistirEquipamento() {
		
		Equipamento equipamento = this.equipamentoRepository.save(new Equipamento());
		
		assertNotNull(equipamento);
	}

}
