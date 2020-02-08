package com.cardoso.controlemanutencao.api.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
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

import com.cardoso.controlemanutencao.api.dtos.EquipamentoDto;
import com.cardoso.controlemanutencao.api.entities.Cliente;
import com.cardoso.controlemanutencao.api.entities.Equipamento;
import com.cardoso.controlemanutencao.api.enums.TipoEnum;
import com.cardoso.controlemanutencao.api.response.Response;
import com.cardoso.controlemanutencao.api.services.ClienteService;
import com.cardoso.controlemanutencao.api.services.EquipamentoService;

@RestController
@RequestMapping("/api/equipamento")
@CrossOrigin(origins = "*")
public class EquipamentoController {
	
	private static final Logger log = LoggerFactory.getLogger( EquipamentoController.class);
	
	@Autowired
	private EquipamentoService equipamentoService;
	
	@Autowired
	private ClienteService clienteService;
	
	
	public EquipamentoController() {
		
	}
	
	/**
	 * Adiciona equipamento
	 * 
	 * @param equipamentoDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<EquipamentoDto>> euquipamento(@Valid @RequestBody EquipamentoDto equipamentoDto, BindingResult result) throws ParseException{
		log.info("Adicionando equipamento : {}", equipamentoDto.toString());
		Response<EquipamentoDto> response = new Response<EquipamentoDto>();
		validadaEquipamento(equipamentoDto, result);
		Equipamento equipamento = this.converterDtoParaEquipamento(equipamentoDto, result);
		
		if(result.hasErrors()) {
			log.info("Erro ao cadastrar equipamento: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.equipamentoService.persistir(equipamento);
		response.setData(this.conveterEquipamentoParaDto(equipamento));
		return ResponseEntity.ok(response);
		
	}
	

	/**
	 * Buscar Equipamento por id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<EquipamentoDto>> buscarPorId(@PathVariable("id") Long id){
		log.info("Buscando equipamento id: {}", id);
		Response<EquipamentoDto> response = new Response<EquipamentoDto>();
		Optional<Equipamento> equipamento = this.equipamentoService.buscarEquipamentoPorId(id);
		
		if(!equipamento.isPresent()) {
			log.info("Não existe equipamento para o id: {}", id);
			response.getErros().add("Equipamento não encontrado para o id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.conveterEquipamentoParaDto(equipamento.get()));
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Lista equipamentos
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Response<List<EquipamentoDto>>> listaEquipamento(){
		
		log.info("Listando equipamentos ...");
		Response<List<EquipamentoDto>> response = new Response<List<EquipamentoDto>>();
		List<Equipamento> listEquipamento = this.equipamentoService.listar();
		if(listEquipamento == null ) {
			log.info("Falha ao carregar a lista de equipamento");
			response.getErros().add("Erro ao carregar a lista de equipamento, por favor contate o suporte");
			return ResponseEntity.badRequest().body(response);
		}
		List<EquipamentoDto> listDto = listEquipamento.stream().map(obj -> new EquipamentoDto(obj)).collect(Collectors.toList());
		response.setData(listDto);
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * Atualiza Equipamento
	 * 
	 * @param id
	 * @param equipamentoDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<EquipamentoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody EquipamentoDto equipamentoDto, BindingResult result) throws ParseException{
		
		log.info("Atualizando equipamento...");
		Response<EquipamentoDto> response = new Response<EquipamentoDto>();
		validadaEquipamento(equipamentoDto, result);
		Optional<Equipamento> equipBD = this.equipamentoService.buscarEquipamentoPorId(id);
		
		if(!equipBD.isPresent()) {
			log.info("Sem retorno para o equuipamnto id: {}", id);
			response.getErros().add("Equipamento não encontrado com o id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		equipamentoDto.setId(id);
		Equipamento equipamento = this.converteAtualizaDtoParaFuncionario(equipamentoDto, result);
		
		if(result.hasErrors()) {
			
			log.info("Erro validando equipamento: {}", result.getAllErrors());
			response.getErros().add("Erro ao atualizar equipamento.  " + id);
			return ResponseEntity.badRequest().body(response);
		}
		equipamento = this.equipamentoService.persistir(equipamento);
		response.setData(this.conveterEquipamentoParaDto(equipamento));
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id){
		log.info("Removendo equipamento id: {}", id);
		Response<String> response = new Response<String>();
		Optional<Equipamento> equipamento = this.equipamentoService.buscarEquipamentoPorId(id);
		
		if(!equipamento.isPresent()) {
			log.info("Equipamento não encontrado, id: {}", id);
			response.getErros().add("Equipamento não encontrado com o id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		this.equipamentoService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	private Equipamento converteAtualizaDtoParaFuncionario(EquipamentoDto equipamentoDto, BindingResult result) {
		
		Equipamento eq = new Equipamento();
		Optional<Equipamento> eqBd = this.equipamentoService.buscarEquipamentoPorId(equipamentoDto.getId());
		
		eq.setCliente(eqBd.get().getCliente());
		eq.setDescricaoDefeito(eqBd.get().getDescricaoDefeito());
		eq.setId(eqBd.get().getId());
		eq.setMarca(eqBd.get().getMarca());
		eq.setTipo(eqBd.get().getTipo());
		
		if(!eq.getCliente().getId().equals(equipamentoDto.getClienteId())) {
			Optional<Cliente> cliente = this.clienteService.buscarClientePorId(equipamentoDto.getClienteId());
			eq.setCliente(cliente.get());
		}
		
		if(!eq.getDescricaoDefeito().equals(equipamentoDto.getDescricaoDefeito())){
			eq.setDescricaoDefeito(equipamentoDto.getDescricaoDefeito());
		}
		
		if(!eq.getMarca().equals(equipamentoDto.getMarca())) {
			eq.setMarca(equipamentoDto.getMarca());
		}
		
		if(!eq.getTipo().toString().equals(equipamentoDto.getTipo())) {
			
			if(EnumUtils.isValidEnum(TipoEnum.class, equipamentoDto.getTipo())) {
				eq.setTipo(TipoEnum.valueOf(equipamentoDto.getTipo()));
			}else {
				result.addError(new ObjectError("tipo", "Tipo inválido"));
			}
			
		}
		
		return eq;
		
	}
	
	/**
	 * Valida se existe cliente cadastrado
	 * 
	 * @param equipamentoDto
	 * @param result
	 */
	private void validadaEquipamento(EquipamentoDto equipamentoDto, BindingResult result){
		
		if(equipamentoDto.getClienteId() !=null) {
			
			Optional<Cliente> cliente = this.clienteService.buscarClientePorId(equipamentoDto.getClienteId());
			if(!cliente.isPresent()) {
				log.info("Erro de validacao, cliente não encontrado para o id: {}", equipamentoDto.getClienteId());
				result.addError(new ObjectError("cliente", "Id cliente não cadastrado"));
			}
		}else {
			log.info("Erro de validacao, cliente id não informado");
			result.addError(new ObjectError("cliente", "Id do cliente não informado"));
		}
	}
	
	/**
	 * Converte equipamento para DTO
	 * 
	 * @param equipamento
	 * @return
	 */
	private EquipamentoDto conveterEquipamentoParaDto(Equipamento equipamento) {
		EquipamentoDto eqDto = new EquipamentoDto();
		eqDto.setId(equipamento.getId());
		eqDto.setMarca(equipamento.getMarca());
		eqDto.setDescricaoDefeito(equipamento.getDescricaoDefeito());
		eqDto.setTipo(equipamento.getTipo().toString());
		eqDto.setClienteId(equipamento.getCliente().getId());
		
		return eqDto;
		
	}
	
	/**
	 * Converte DTO para equipamento
	 * 
	 * @param equipamentoDto
	 * @param result
	 * @return
	 */
	private Equipamento converterDtoParaEquipamento(EquipamentoDto equipamentoDto, BindingResult result) {
		Equipamento eqp  = new Equipamento();
		eqp.setMarca(equipamentoDto.getMarca());
		eqp.setDescricaoDefeito(equipamentoDto.getDescricaoDefeito());
		Cliente cli = new Cliente();
		cli.setId(equipamentoDto.getClienteId());
		eqp.setCliente(cli);
		
		if(EnumUtils.isValidEnum(TipoEnum.class, equipamentoDto.getTipo())) {
			eqp.setTipo(TipoEnum.valueOf(equipamentoDto.getTipo()));
		}else {
			result.addError(new ObjectError("tipo", "Tipo inválido"));
		}
		
		return eqp;
	}
}
