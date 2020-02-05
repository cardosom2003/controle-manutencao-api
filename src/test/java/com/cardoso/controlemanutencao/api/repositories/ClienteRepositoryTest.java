package com.cardoso.controlemanutencao.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cardoso.controlemanutencao.api.entities.Cliente;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteRepositoryTest {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	private static final String NOME = "Fulano";
	private static final String EMAIL = "fulano@email.com";
	private static final String TELEFONE ="36985-2185";
	
	@Before
	public void setUp() throws Exception{
		
		Cliente cliente = new Cliente();
		cliente.setEmail(EMAIL);
		cliente.setNome(NOME);
		cliente.setTelefone(TELEFONE);
		cliente.setEndereco("Rua de Baixo");
		this.clienteRepository.save(cliente);
	}
	
	@After
	public final void tearDown() {
		this.clienteRepository.deleteAll();
	}
	
	@Test
	public void testBuscarPorTelefone() {
		Cliente cliente = this.clienteRepository.findByTelefone(TELEFONE);
		
		assertEquals(TELEFONE, cliente.getTelefone() );
	}
	
	@Test
	public void testBuscarPorEmail() {
		Cliente cliente = this.clienteRepository.findByEmail(EMAIL);
		
		assertEquals(EMAIL, cliente.getEmail() );
	}
	
	@Test
	public void testBuscarPorEmailOrTelefoneComTelefoneInvalido() {
		Cliente cliente = this.clienteRepository.findByEmailOrTelefone(EMAIL, null);
		
		assertNotNull(cliente);
	}
	
	@Test
	public void testBuscarPorEmailOrTelefoneComEmailInvalido() {
		Cliente cliente = this.clienteRepository.findByEmailOrTelefone(null, TELEFONE);
		
		assertNotNull(cliente);
	}

}
