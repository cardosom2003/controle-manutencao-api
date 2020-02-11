package com.cardoso.controlemanutencao.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cardoso.controlemanutencao.api.entities.Funcionario;
import com.cardoso.controlemanutencao.api.enums.PerfilEnum;
import com.cardoso.controlemanutencao.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	private static final String NOME = "Nome Funcionario";
	private static final String EMAIL = "nome@empresa.com.br";
	private static final String SENHA = "123456";
	
	@Before
	public void setUp() throws Exception{
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(NOME);
		funcionario.setEmail(EMAIL);
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(SENHA));
		this.funcionarioRepository.save(funcionario);
		
	}
	
	@After
	public final void tearDown() {
		this.funcionarioRepository.deleteAll();
	}
	
	
	@Test
	public void testBuscarfuncionarioPorNome() {
		Funcionario funcionario = this.funcionarioRepository.findByNome(NOME);
		
		assertEquals(NOME, funcionario.getNome());
	}
	
	public void testBuscarFuncionarioPorEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
		assertEquals(EMAIL, funcionario.getEmail());
	}

}
