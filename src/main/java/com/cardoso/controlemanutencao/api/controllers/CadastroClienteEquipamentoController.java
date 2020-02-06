package com.cardoso.controlemanutencao.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardoso.controlemanutencao.api.dtos.CadastroClienteEquipamentoDto;
import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.entities.Equipamento;
import com.cardoso.controlemanutencao.api.enums.TipoEnum;
import com.cardoso.controlemanutencao.api.response.Response;
import com.cardoso.controlemanutencao.api.services.ClienteService;
import com.cardoso.controlemanutencao.api.services.EquipamentoService;

@RestController
@RequestMapping("/api/cadastrar")
@CrossOrigin(origins ="*")
public class CadastroClienteEquipamentoController {
	
	private static final Logger log = LoggerFactory.getLogger( CadastroClienteEquipamentoController.class);
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EquipamentoService equipamentoService;

	
	public CadastroClienteEquipamentoController() {
		
	}
	
	@PostMapping
	public ResponseEntity<Response<CadastroClienteEquipamentoDto>> cadastrar(@Valid @RequestBody 
			CadastroClienteEquipamentoDto cadastroClienteEquipamentoDto, BindingResult result ) throws NoSuchAlgorithmException{
		
		log.info("Cadastrondo Cliente e Equipamento: {}", cadastroClienteEquipamentoDto.toString());
		Response<CadastroClienteEquipamentoDto> response = new Response<CadastroClienteEquipamentoDto>();
		
		validarDadosExistentes(cadastroClienteEquipamentoDto, result);
		Cliente cliente = this.converterDtoParaCliente(cadastroClienteEquipamentoDto);
		Equipamento equipamento = this.converterDtoParaEquipamento(cadastroClienteEquipamentoDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro ao validar dados de cadastro : {}", result.getAllErrors());
			return ResponseEntity.badRequest().body(response);
		}
		
		this.clienteService.persistir(cliente);
		equipamento.setCliente(cliente);
		this.equipamentoService.persistir(equipamento);
		
		response.setData(this.converterCadastroClienteEquipamentoDto(cliente));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Verifica se existe cliente cadastrado na base de dados
	 * com o telefone ou e-mail informado durante o cadastro
	 * 
	 * @param cadastroClienteEmpresaDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroClienteEquipamentoDto cadastroClienteEmpresaDto, BindingResult result) {
		this.clienteService.buscarPorClienteEmail(cadastroClienteEmpresaDto.getEmail())
			.ifPresent(cli -> result.addError(new ObjectError("cliente", "Existe Cliente cadastrado com o e-mail informado")));
		
		this.clienteService.buscarClientePorTelefone(cadastroClienteEmpresaDto.getTelefone())
		.ifPresent(cli -> result.addError(new ObjectError("Cliente", "Existe Cliente cadastrado com o telefone informado")));
	}
	
	/**
	 * Método que extrai do DTO os dados referente ao cliente 
	 * para criar um objeto do tip Cliente
	 * 
	 * @param cadastroClienteEquipamentoDto
	 * @return
	 */
	private Cliente converterDtoParaCliente(CadastroClienteEquipamentoDto cadastroClienteEquipamentoDto) {
		
		Cliente cliente = new Cliente();
		cliente.setNome(cadastroClienteEquipamentoDto.getNome());
		cliente.setEmail(cadastroClienteEquipamentoDto.getEmail());
		cliente.setTelefone(cadastroClienteEquipamentoDto.getTelefone());
		cliente.setEndereco(cadastroClienteEquipamentoDto.getEndereco());
		
		return cliente;
		
	}
	
	private  Equipamento converterDtoParaEquipamento(CadastroClienteEquipamentoDto cadastroClienteEquipamentoDto,BindingResult result) {
		
		Equipamento equipamento = new Equipamento();
		equipamento.setMarca(cadastroClienteEquipamentoDto.getMarca());
		equipamento.setTipo(TipoEnum.AUDIO_VIDEO);
		equipamento.setDescricaoDefeito(cadastroClienteEquipamentoDto.getDescricaoDefeito());
		
		return equipamento;
	}
	
	private CadastroClienteEquipamentoDto converterCadastroClienteEquipamentoDto(Cliente cliente) {
		CadastroClienteEquipamentoDto cadDto = new CadastroClienteEquipamentoDto();
		cadDto.setId(cliente.getId());
		cadDto.setNome(cliente.getNome());
		cadDto.setEmail(cliente.getEmail());
		cadDto.setTelefone(cliente.getTelefone());
		cadDto.setEndereco(cliente.getEndereco());
		
		return cadDto;
	}
}
