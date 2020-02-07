package com.cardoso.controlemanutencao.api.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardoso.controlemanutencao.api.dtos.ClienteDto;
import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.response.Response;
import com.cardoso.controlemanutencao.api.services.ClienteService;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {
	
	private static final Logger log = LoggerFactory.getLogger( ClienteController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	
	public ClienteController() {
		
	}
	
	/**
	 * Adiciona um novo cliente
	 * 
	 * @param clienteDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<ClienteDto>> cliente(@Valid @RequestBody ClienteDto clienteDto, BindingResult result) throws ParseException{
		log.info("Adicionando clinete: {}", clienteDto.toString());
		Response<ClienteDto> response = new Response<ClienteDto>();
		validarCliente(clienteDto, result);
		Cliente cliente = this.converterDtoParaCliente(clienteDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro ao cadastrar cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.clienteService.persistir(cliente);
		response.setData(this.converterClienteParaDto(cliente));
		
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Buscar cliente por id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ClienteDto>> buscarPorId(@PathVariable("id") Long id){
		log.info("Buscando clinete por id: {}", id);
		Response<ClienteDto> response = new Response<ClienteDto>();
		Optional<Cliente> cliente = this.clienteService.buscarClientePorId(id);
		
		if(!cliente.isPresent()) {
			log.info("Lançamento não encontrado para o ID: {}", id);
			response.getErros().add("Cliente não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterClienteParaDto(cliente.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Lista todos os Cliente Cadastrado no Banco
	 * 
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<List<ClienteDto>> listar(){
		
		log.info("Listando clientes");
		List<Cliente> listCliente = this.clienteService.listarCliente();
		List<ClienteDto> listDto = listCliente.stream().map(obj -> new ClienteDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	
	/**
	 * Atualiza um Cliente passando o id
	 * 
	 * @param id
	 * @param clienteDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<ClienteDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody ClienteDto clienteDto, BindingResult result) throws ParseException{
		log.info("Adicionando clinete: {}", clienteDto.toString());
		Response<ClienteDto> response = new Response<ClienteDto>();
		validarCliente(clienteDto, result);
		Cliente cliente = this.converterDtoParaCliente(clienteDto, result);
		
		if(result.hasErrors()) {
			log.info("Erro validando cliente: {}", result.getAllErrors());
			response.getErros().add("Erro ao atualizar cliente. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		cliente = this.clienteService.persistir(cliente);
		response.setData(this.converterClienteParaDto(cliente));
		return ResponseEntity.ok(response);
		
	}
	/**
	 * Remove Cliente por ID
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id){
		log.info("Removendo cliente id: {}", id);
		Response<String> response = new Response<String>();
		Optional<Cliente> cliente = this.clienteService.buscarClientePorId(id);
		
		if(!cliente.isPresent()) {
			log.info("Erro ao remover devido ao cliente ID: {} ser inválido.", id);
			response.getErros().add("Erro ao remover cliente. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);

		}
		
		this.clienteService.remover(id);
		return ResponseEntity.ok(new Response<String>());

	}
	
	/**
	 * Valida se existe cliente cadastrado com o telefone ou e-mail informado
	 * 
	 * @param clienteDto
	 * @param result
	 */
	private void validarCliente(ClienteDto clienteDto, BindingResult result) {
		
		this.clienteService.buscarPorClienteEmail(clienteDto.getEmail())
		.ifPresent(cli -> result.addError(new ObjectError("cliente", "Existe Cliente cadastrado com o e-mail informado")));
	
		this.clienteService.buscarClientePorTelefone(clienteDto.getTelefone())
		.ifPresent(cli -> result.addError(new ObjectError("Cliente", "Existe Cliente cadastrado com o telefone informado")));
		
	}
	
	/**
	 * Converte DTO para cliente
	 * 
	 * @param clienteDto
	 * @param result
	 * @return
	 */
	private Cliente converterDtoParaCliente(ClienteDto clienteDto, BindingResult result) {
		Cliente cliente = new Cliente();
		cliente.setNome(clienteDto.getNome());
		cliente.setEmail(clienteDto.getEmail());
		cliente.setTelefone(clienteDto.getTelefone());
		cliente.setEndereco(clienteDto.getEndereco());
		return cliente;
	}
	
	/**
	 * Converte Cliente para DTO
	 * 
	 * @param cliente
	 * @return
	 */
	private ClienteDto converterClienteParaDto(Cliente cliente) {
		ClienteDto cliDto = new ClienteDto();
		cliDto.setId(cliente.getId());
		cliDto.setNome(cliente.getNome());
		cliDto.setEmail(cliente.getEmail());
		cliDto.setTelefone(cliente.getTelefone());
		cliDto.setEndereco(cliente.getEndereco());
		return cliDto;
	}
	
	

}
