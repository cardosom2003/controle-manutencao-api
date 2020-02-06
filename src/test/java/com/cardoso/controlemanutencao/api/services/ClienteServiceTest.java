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
import com.cardoso.controlemanutencao.api.repositories.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
	
	@MockBean
	private ClienteRepository clienteRepositoy;
	
	@Autowired
	private ClienteService clienteService;
	
	private static final String EMAIL = "cleinte@email.com";
	private static final String TELEFONE = "048999999999";
	
	@Before
	public void setUp() throws Exception{
		BDDMockito.given(this.clienteRepositoy.findByEmail(Mockito.anyString())).willReturn(new Cliente());
		BDDMockito.given(this.clienteRepositoy.findByTelefone(Mockito.anyString())).willReturn(new Cliente());
		BDDMockito.given(this.clienteRepositoy.findByEmailOrTelefone(Mockito.anyString(), Mockito.anyString()))
		.willReturn(new Cliente());
		BDDMockito.given(this.clienteRepositoy.save(Mockito.any(Cliente.class))).willReturn(new Cliente());
	}
	
	@Test
	public void testBuscarEmpresaPorEmail() {
		Optional<Cliente> cliente = this.clienteService.buscarPorClienteEmail(EMAIL);
		
		assertTrue(cliente.isPresent());
	}
	
	@Test
	public void testBuscarEmpresaPorTelefone() {
		Optional<Cliente> cliente = this.clienteService.buscarClientePorTelefone(TELEFONE);
		
		assertTrue(cliente.isPresent());
	}
	
	@Test
	public void testBuscarEmpresaPorEmailOrTelefone() {
		Optional<Cliente> cliente = this.clienteService.buscarClientePorEmailOrTelefone(EMAIL, TELEFONE);
		
		assertTrue(cliente.isPresent());
	}

	@Test
	public void testBuscarEmpresaPorEmailOrTelefoneComEmailInvalido() {
		Optional<Cliente> cliente = this.clienteService.buscarClientePorEmailOrTelefone("", TELEFONE);
		
		assertTrue(cliente.isPresent());
	}
	
	@Test
	public void testBuscarEmpresaPorEmailOrTelefoneComTelefoneInvalido() {
		Optional<Cliente> cliente = this.clienteService.buscarClientePorEmailOrTelefone(EMAIL, "");
		
		assertTrue(cliente.isPresent());
	}
	
	public void testPersistirCliente() {
		Cliente cliente = this.clienteService.persistir(new Cliente());
		
		assertNotNull(cliente);
	}

}
