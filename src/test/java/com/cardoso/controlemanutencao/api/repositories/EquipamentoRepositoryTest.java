package com.cardoso.controlemanutencao.api.repositories;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.entities.Equipamento;
import com.cardoso.controlemanutencao.api.enums.TipoEnum;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EquipamentoRepositoryTest {
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	private Long equipamentoId;
	private String equipamentoMarca;
	
	Cliente cliente;
	
	@Before
	public void setUp() throws Exception {
		cliente = this.clienteRepository.save(obterDadosCliente());
		
		Equipamento equip = this.equipamentoRepository.save(obterDadosEquipamento(cliente));
		
		equipamentoId = equip.getId();
		equipamentoMarca = equip.getMarca();
		
		
	}

	@After
	public void tearDown() throws Exception {
		this.clienteRepository.deleteAll();
		this.equipamentoRepository.deleteAll();
	}

	@Test
	public void testBuscarEquipamentoPorMarca() {
		
		Equipamento eq = equipamentoRepository.findByMarca(equipamentoMarca);
		
		assertNotNull(eq);
	}
	
	@Test
	public void testBuscarEquipamentoPorId() {
		
		Optional<Equipamento> eq = equipamentoRepository.findById(equipamentoId);
		
		assertNotNull(eq);
	}
	
	@Test
	public void testBuscarEquipamentoPorCliente() {
		
		Equipamento eq = equipamentoRepository.findByCliente(cliente);
		
		assertNotNull(eq);
	}
	
	
	private Cliente obterDadosCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome("Beltrano");
		cliente.setEmail("beltrano@email.com");
		cliente.setTelefone("0419999-2555");
		cliente.setEndereco("Rua sem Saída SN");
		
		return cliente;
	}
	
	
	private Equipamento obterDadosEquipamento(Cliente cliente) {
		
		Equipamento equipamento = new Equipamento();
		equipamento.setTipo(TipoEnum.AUDIO_VIDEO);
		equipamento.setMarca("Sony");
		equipamento.setDescricaoDefeito("Não está ligando!");
		equipamento.setCliente(cliente);
		return equipamento;
		
	}
}
