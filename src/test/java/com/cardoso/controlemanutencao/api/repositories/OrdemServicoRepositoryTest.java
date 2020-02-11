package com.cardoso.controlemanutencao.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

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
import com.cardoso.controlemanutencao.api.entities.Funcionario;
import com.cardoso.controlemanutencao.api.entities.OrdemServico;
import com.cardoso.controlemanutencao.api.enums.PerfilEnum;
import com.cardoso.controlemanutencao.api.enums.StatusEnum;
import com.cardoso.controlemanutencao.api.enums.TipoEnum;
import com.cardoso.controlemanutencao.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class OrdemServicoRepositoryTest {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	private Long funcionarioId;
	private Long equipamentoId;
	
	@Before
	public void setUp() throws Exception{
		
		Funcionario func = this.funcionarioRepository.save(obterDadosFuncionario());
		funcionarioId = func.getId();
		
		Equipamento eqp = this.equipamentoRepository.save(obterDadosEquipamento());
		equipamentoId = eqp.getId();
		
		OrdemServico ordem = new OrdemServico();
		ordem.setFuncionario(func);
		ordem.setEquipamento(eqp);
		ordem.setOrdemStatus(StatusEnum.PENDENTE);
		ordem.setDataCriacao(new Date());
		
		this.ordemServicoRepository.save(ordem);
		
		
	}
	
	@After
	public void tearDown() throws Exception {
		this.funcionarioRepository.deleteAll();
		this.equipamentoRepository.deleteAll();
		this.clienteRepository.deleteAll();
		
	}
	
	@Test
	public void buscarOrdemServicoPorFuncionarioId() {
		List<OrdemServico> ordem = this.ordemServicoRepository.findByFuncionarioId(funcionarioId);
		
		assertEquals(1, ordem.size());
	}

	@Test
	public void buscarOrdemServicoPorEquipamentoId() {
		List<OrdemServico> ordem = this.ordemServicoRepository.findByEquipamentoId(equipamentoId);
		
		assertEquals(1, ordem.size());
	}
	
	
	public Funcionario obterDadosFuncionario() {
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Jão");
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setEmail("joão@empresa.com.br");
		funcionario.setPerfil(PerfilEnum.ROLE_TECNICO);
		this.funcionarioRepository.save(funcionario);
				
		return funcionario;
	}
	
	public Equipamento obterDadosEquipamento() {
		
		Equipamento equipamento = new Equipamento();
		
		equipamento.setCliente(obterDadosCliente());
		equipamento.setMarca("Nokia");
		equipamento.setTipo(TipoEnum.TELEFONIA);
		equipamento.setDescricaoDefeito("Não conecta na rede");
		
		return equipamento;
	}
	
	public Cliente obterDadosCliente() {
		
		Cliente cliente = new Cliente();
		cliente.setNome("Maria dos Anjos");
		cliente.setEndereco("Rua dos lado");
		cliente.setEmail("maria@email.com");
		cliente.setTelefone("123512");
		
		this.clienteRepository.save(cliente);
		return cliente;
	}

}
