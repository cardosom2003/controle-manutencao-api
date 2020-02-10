package com.cardoso.controlemanutencao.api.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardoso.controlemanutencao.api.dtos.FuncionarioDto;
import com.cardoso.controlemanutencao.api.dtos.OrdemServicoDto;
import com.cardoso.controlemanutencao.api.entities.Equipamento;
import com.cardoso.controlemanutencao.api.entities.Funcionario;
import com.cardoso.controlemanutencao.api.entities.OrdemServico;
import com.cardoso.controlemanutencao.api.enums.PerfilEnum;
import com.cardoso.controlemanutencao.api.enums.StatusEnum;
import com.cardoso.controlemanutencao.api.response.Response;
import com.cardoso.controlemanutencao.api.services.EquipamentoService;
import com.cardoso.controlemanutencao.api.services.FuncionarioService;
import com.cardoso.controlemanutencao.api.services.OrdemServicoService;

@RestController
@RequestMapping("/api/os")
@CrossOrigin(origins = "*")
public class OrdemServicoController {
	
	private static final Logger log = LoggerFactory.getLogger( OrdemServicoController.class);
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@Autowired
	private EquipamentoService equipamentoService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	
	public OrdemServicoController() {
		
	}
	
	/**
	 * Adiciona ordem de serviço
	 * 
	 * @param ordemServicoDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PostMapping
	public ResponseEntity<Response<OrdemServicoDto>> ordemServico(@Valid @RequestBody OrdemServicoDto ordemServicoDto,
			BindingResult result) throws ParseException{
		log.info("Adicionando ordem de servico: {}", ordemServicoDto.toString());
		Response<OrdemServicoDto> response = new Response<OrdemServicoDto>();
		validarOrdemServico(ordemServicoDto, result);
		
		OrdemServico ordem = this.converterDtoParaOrdemServico(ordemServicoDto, result);
		
		if(result.hasErrors()) {
			log.info("Erro ao  cadastrar ordem de servico: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.ordemServicoService.persistir(ordem);
		response.setData(this.converterOrdeParaDto(ordem));
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<OrdemServicoDto>> buscarPorId(@PathVariable("id") Long id){
		log.info("Buscando ordem de servico id {}", id);
		Response<OrdemServicoDto> response = new Response<OrdemServicoDto>();
		Optional<OrdemServico> ordem = this.ordemServicoService.buscarOrdemServicoPorId(id);
		
		if(!ordem.isPresent()) {
			log.info("Não encontrada ordem de serviço id: {}", id);
			response.getErros().add("Ordem de serviço não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterOrdeParaDto(ordem.get()));
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Lista Ordem servico por funcionario
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/funcionario/{id}")
	public ResponseEntity<Response<List<OrdemServicoDto>>> orderPorFuncionario(@PathVariable("id") Long id){
		log.info("Buscando ordens de servico por funcionario");
		Response<List<OrdemServicoDto>> response = new Response<List<OrdemServicoDto>>();
		List<OrdemServico> lista = this.ordemServicoService.BuscarOSPorFuncionarioId(id);
		if(lista == null) {
			log.info("Erro ao listar ordem de servico");
			response.getErros().add("Erro ao carregar lista de ordem de servico por id funcionario");
			return ResponseEntity.badRequest().body(response);
		}
		List<OrdemServicoDto> listaDto = lista.stream().map(obj -> new OrdemServicoDto(obj)).collect(Collectors.toList());
		response.setData(listaDto);
		return ResponseEntity.ok().body(response);
		
	}
	
	/**
	 * Lista orde de serviço por quipamento id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "equipamento/{id}")
	public ResponseEntity<Response<List<OrdemServicoDto>>> ordemPorEquipamento(@PathVariable("id") long id){
		log.info("Listando ordens de servico por id equipamento");
		Response<List<OrdemServicoDto>> response = new Response<List<OrdemServicoDto>>();
		List<OrdemServico> lista = this.ordemServicoService.BuscarOSPorEquipamentoId(id);
		if(lista == null) {
			log.info("Erro ao listar orde de servico");
			response.getErros().add("Erro ao carregar lista de ordem de sevico por id equipamento");
		}
		
		List<OrdemServicoDto> listaDto = lista.stream().map(obj -> new OrdemServicoDto(obj)).collect(Collectors.toList());
		response.setData(listaDto);
		return ResponseEntity.ok().body(response);
	}
	
	/**
	 * Listar ordem de servico
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Response<List<OrdemServicoDto>>> listar(){
		log.info("Listando ordens de servico");
		Response<List<OrdemServicoDto>> response = new Response<List<OrdemServicoDto>>();
		List<OrdemServico> listaOrdem = this.ordemServicoService.listar();
		if(listaOrdem == null) {
			log.info("Erro ao listar ordem de serviço");
			response.getErros().add("Erro ao carregar listar ordem de serviço, por favor contate o suporte" );
			return ResponseEntity.badRequest().body(response);
		}
		List<OrdemServicoDto> listaDto = listaOrdem.stream().map(obj -> new OrdemServicoDto(obj)).collect(Collectors.toList());
		response.setData(listaDto);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Atualizar Ordem Sevico
	 * 
	 * @param id
	 * @param ordemServicoDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<OrdemServicoDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody OrdemServicoDto ordemServicoDto, BindingResult result) throws ParseException{
		log.info("Atualizando ordem de serviço id: {}", id);
		Response<OrdemServicoDto> response = new Response<OrdemServicoDto>();
		Optional<OrdemServico> ordem = this.ordemServicoService.buscarOrdemServicoPorId(id);
		
		if(!ordem.isPresent()){
			log.info("Ordem de serviço nao encontrada id: {}", id);
			response.getErros().add("Ordem de seriçõ não encontrda, favor informar na base de dados");
			return ResponseEntity.badRequest().body(response);
		}
		
		ordemServicoDto.setId(id);
		OrdemServico os = this.converterAtualizarDtoParaOrdemServico(ordemServicoDto, result);
		
		if(result.hasErrors()) {
			
			log.info("Erro atualizando ordem de servico: {}", result.getAllErrors());
			response.getErros().add("Erro ao atualizar ordem servico:  " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		os = this.ordemServicoService.persistir(os);
		response.setData(this.converterOrdeParaDto(os));
		
		return ResponseEntity.ok(response);
	}
	
	private OrdemServico converterAtualizarDtoParaOrdemServico(OrdemServicoDto ordemDto, BindingResult result) throws ParseException {
		log.info("Atualizando ordem de serviço id: {}", ordemDto.getId());
		Optional<OrdemServico> ordemBd = this.ordemServicoService.buscarOrdemServicoPorId(ordemDto.getId());
		OrdemServico ordemAtt = new OrdemServico();
				
		ordemAtt.setId(ordemBd.get().getId());
		ordemAtt.setConclusaoServico(ordemBd.get().getConclusaoServico());
		ordemAtt.setDataCriacao(ordemBd.get().getDataCriacao());
		ordemAtt.setEquipamento(ordemBd.get().getEquipamento());
		ordemAtt.setFuncionario(ordemBd.get().getFuncionario());
		ordemAtt.setId(ordemBd.get().getId());
		ordemAtt.setInicioServico(ordemBd.get().getInicioServico());
		ordemAtt.setNotaConclusao(ordemBd.get().getNotaConclusao());
		ordemAtt.setOrdemStatus(ordemBd.get().getOrdemStatus());
		
		if(ordemDto.getConclusaoServico() !=null ) {
			ordemAtt.setConclusaoServico(this.dateFormat.parse(ordemDto.getConclusaoServico()));
		}
		
		if(ordemDto.getInicioServico() !=null) {
			ordemAtt.setInicioServico(this.dateFormat.parse(ordemDto.getInicioServico().toString()));
		}
		
		if(ordemDto.getOrdemStatus() != null) {
			if (EnumUtils.isValidEnum(StatusEnum.class, ordemDto.getOrdemStatus())) {
				ordemAtt.setOrdemStatus(StatusEnum.valueOf(ordemDto.getOrdemStatus()));
			} else {
				result.addError(new ObjectError("status", "Status inválido."));
			}
		}
			//	ordemAtt.setDataCriacao(this.dateFormat.parse(ordemDto.getDataCriacao()));
		
		if(ordemDto.getNotaConclusao() !=null) {
			
			ordemAtt.setNotaConclusao(ordemDto.getNotaConclusao());
		}
		
		if(ordemDto.getEquipamentoId() != null ) {
			Optional<Equipamento> equi = this.equipamentoService.buscarEquipamentoPorId(ordemDto.getEquipamentoId());
			ordemAtt.setEquipamento(equi.get());
		}
		
		if(ordemDto.getFuncionarioId() != null ) {
			Optional<Funcionario> func = this.funcionarioService.buscarFuncionarioPorId(ordemDto.getFuncionarioId());
			ordemAtt.setFuncionario(func.get());
		}
		
		
		return ordemAtt;
	}
	
	/**
	 * Converte Ordem de Servico para DTO
	 * 
	 * @param ordem
	 * @return
	 */
	private OrdemServicoDto converterOrdeParaDto(OrdemServico ordem) {
		OrdemServicoDto ordemDto = new OrdemServicoDto();
		
		ordemDto.setId(ordem.getId());
		ordemDto.setEquipamentoId(ordem.getEquipamento().getId());
		ordemDto.setFuncionarioId(ordem.getFuncionario().getId());
		ordemDto.setDataCriacao(this.dateFormat.format(ordem.getDataCriacao()));
		if(ordem.getInicioServico() != null) {
			ordemDto.setInicioServico(this.dateFormat.format(ordem.getInicioServico()));
		}
		if(ordem.getConclusaoServico() != null) {
			ordemDto.setConclusaoServico(this.dateFormat.format(ordem.getConclusaoServico()));
		}
		
		ordemDto.setNotaConclusao(ordem.getNotaConclusao());
		
		return ordemDto;
	}
	
	/**
	 * Converte de DTO para Ordem de Servico
	 * 
	 * @param ordemServicoDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	private OrdemServico converterDtoParaOrdemServico(OrdemServicoDto ordemServicoDto, BindingResult result) throws ParseException {
		OrdemServico ordem = new OrdemServico();
		ordem.setDataCriacao(new Date());
		
		if(ordemServicoDto.getInicioServico() != null) {
			ordem.setInicioServico(this.dateFormat.parse(ordemServicoDto.getInicioServico()));
		}
		
		if(ordem.getConclusaoServico() != null) {
			
			ordem.setConclusaoServico(this.dateFormat.parse(ordemServicoDto.getConclusaoServico()));
		}
		
		if(ordemServicoDto.getFuncionarioId() != null) {
			Optional<Funcionario> funcionario = this.funcionarioService.buscarFuncionarioPorId(ordemServicoDto.getFuncionarioId());
			
			if(funcionario.isPresent()) {
				ordem.setFuncionario(funcionario.get());
			}else {
				
				result.addError(new ObjectError("funcionario","Funcionario id não existe"));
			}
		}
		
		if(ordemServicoDto.getEquipamentoId() != null) {
			
			Optional<Equipamento> equipamento = this.equipamentoService.buscarEquipamentoPorId(ordemServicoDto.getEquipamentoId());
			
			ordem.setEquipamento(equipamento.get());
		}
		
		ordem.setNotaConclusao(ordemServicoDto.getNotaConclusao());
		
		if (EnumUtils.isValidEnum(StatusEnum.class, ordemServicoDto.getOrdemStatus())) {
			ordem.setOrdemStatus(StatusEnum.valueOf(ordemServicoDto.getOrdemStatus()));
		} else {
			result.addError(new ObjectError("status", "Status ordem servico inválido."));
		}

		return ordem;
		
	}
	
	/**
	 * Valida se existe um equipamento cadastrado
	 * 
	 * @param ordem
	 * @param result
	 */
	private void validarOrdemServico(OrdemServicoDto ordem, BindingResult result) {
		log.info("Validando ordem de servico: {}", ordem);
		
		if(ordem.getEquipamentoId() != null) {
			
			Optional<Equipamento> equipamento = this.equipamentoService.buscarEquipamentoPorId(ordem.getEquipamentoId());
			if(!equipamento.isPresent()) {
				log.info("Equipamento não encontrado para o id: {}", ordem.getEquipamentoId());
				result.addError(new ObjectError("equipamento", "Não existe equipamento cadastrado com o id informado"));
			}
		}else {
			log.info("Id do equipamento não informado");
			result.addError(new ObjectError("equipamento", "Equipamento id não informado"));
		}
		
		if(ordem.getFuncionarioId() != null) {
			
			Optional<Funcionario> funcionario = this.funcionarioService.buscarFuncionarioPorId(ordem.getFuncionarioId());
			if(!funcionario.isPresent()) {
				log.info("Funcionario não encontrado para o id: {}", ordem.getFuncionarioId());
				result.addError(new ObjectError("funcioanario", "Não existe funcionario cadastrado com o id informado"));
			}
		}else {
			log.info("Id do funcionario não informado");
			result.addError(new ObjectError("funcionario", "Funcionario id não informado"));
		}
		
		
		
	}

}
