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

import com.cardoso.controlemanutencao.api.dtos.FuncionarioDto;
import com.cardoso.controlemanutencao.api.dtos.FuncionarioDto;
import com.cardoso.controlemanutencao.api.dtos.FuncionarioDto;
import com.cardoso.controlemanutencao.api.dtos.FuncionarioDto;
import com.cardoso.controlemanutencao.api.entities.Funcionario;
import com.cardoso.controlemanutencao.api.entities.Funcionario;
import com.cardoso.controlemanutencao.api.entities.Funcionario;
import com.cardoso.controlemanutencao.api.enums.PerfilEnum;
import com.cardoso.controlemanutencao.api.enums.TipoEnum;
import com.cardoso.controlemanutencao.api.response.Response;
import com.cardoso.controlemanutencao.api.services.FuncionarioService;
import com.cardoso.controlemanutencao.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/funcionario")
@CrossOrigin(origins = "*")
public class FuncionarioController {
	
	private static final Logger log = LoggerFactory.getLogger( FuncionarioController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	
	@PostMapping
	public ResponseEntity<Response<FuncionarioDto>> funcionario(@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws ParseException{
		log.info("Adicionando funcionario: {}", funcionarioDto.toString());
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();
		
		Funcionario funcionario = this.converterDtoParaFuncionario(funcionarioDto, result);
		
		if(result.hasErrors()) {
			log.error("Erro ao cadastrar funcionario: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.funcionarioService.persistir(funcionario);
		response.setData(this.converterFuncionarioParaDto(funcionario));
		
		return ResponseEntity.ok(response);
		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id){
		log.info("Removendo funcionario id: {}", id);
		Response<String> response = new Response<String>();
		Optional<Funcionario> funcionario = this.funcionarioService.buscarFuncionarioPorId(id);
		
		if(!funcionario.isPresent()) {
			log.info("Erro ao remover devido ao funcionario ID: {} ser inválido.", id);
			response.getErros().add("Erro ao remover cliente. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		this.funcionarioService.remove(id);
		return ResponseEntity.ok(new Response<String>());
		
	}
	
	/**
	 * Buscar funcionario por id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> buscarPorId(@PathVariable("id") Long id){
		log.info("Buscando clinete por id: {}", id);
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();
		Optional<Funcionario> funcionario = this.funcionarioService.buscarFuncionarioPorId(id);
		
		if(!funcionario.isPresent()) {
			log.info("Lançamento não encontrado para o ID: {}", id);
			response.getErros().add("Funcionario não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterFuncionarioParaDto(funcionario.get()));
		return ResponseEntity.ok(response);
	}
	/*
	@GetMapping
	public ResponseEntity<List<FuncionarioDto>> listar(){
		log.info("Listando funionarios");
		List<Funcionario> listFuncionario = this.funcionarioService.listar();
		List<FuncionarioDto> listDto = listFuncionario.stream().map(obj -> new FuncionarioDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	
	}
	*/
	
	/**
	 * Atualiza um Funcionario passando o id
	 * 
	 * @param id
	 * @param funcionarioDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody FuncionarioDto funcionarioDto, BindingResult result) throws ParseException{
		log.info("Atualizando clinete: {}", funcionarioDto.toString());
		
		Response<FuncionarioDto> response = new Response<FuncionarioDto>();
		Optional<Funcionario> funcionarioBase = this.funcionarioService.buscarFuncionarioPorId(id);
		if(!funcionarioBase.isPresent()) {
			log.info("Funcionario não encontrado para o id: {}", id);
			response.getErros().add("Erro ao atualizar funcionario. Registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		 funcionarioDto.setId(id);
		Funcionario funcionario = this.converteAtualizaDtoParaFuncionario(funcionarioDto, result);
		
		if(result.hasErrors()) {
			
			log.info("Erro validando funcionario: {}", result.getAllErrors());
			response.getErros().add("Erro ao atualizar funcionario.  " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		funcionario = this.funcionarioService.persistir(funcionario);
		response.setData(this.converterFuncionarioParaDto(funcionario));
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Converte DTO para Funcionario
	 * 
	 * @param funcionarioDto
	 * @param result
	 * @return
	 */
	private Funcionario converterDtoParaFuncionario(FuncionarioDto funcionarioDto, BindingResult result) {
		
		Funcionario funcionario = new Funcionario();
		
		funcionario.setNome(funcionarioDto.getNome());
		
		if (EnumUtils.isValidEnum(PerfilEnum.class, funcionarioDto.getPerfil())) {
			funcionario.setPerfil(PerfilEnum.valueOf(funcionarioDto.getPerfil()));
		} else {
			result.addError(new ObjectError("perfil", "Perfil inválido."));
		}
		
		funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha()));
		
		return funcionario;
	}
	
	/**
	 * Converte Funcionario para DTO
	 * 
	 * @param func
	 * @return
	 */
	private FuncionarioDto converterFuncionarioParaDto(Funcionario func) {
		FuncionarioDto funcDto = new FuncionarioDto();
		funcDto.setId(func.getId());
		funcDto.setNome(func.getNome());
		funcDto.setPerfil(func.getPerfil().toString());
		
		return funcDto;
		
	}
	
	/**
	 * Atualiza e converte DTO para funcionario
	 * 
	 * @param funcionarioDto
	 * @param result
	 * @return
	 * 
	 */
	private Funcionario converteAtualizaDtoParaFuncionario(FuncionarioDto funcionarioDto, BindingResult result) {
		Optional<Funcionario> funBD = this.funcionarioService.buscarFuncionarioPorId(funcionarioDto.getId());
		Funcionario funAtualizar = new Funcionario();
		funAtualizar.setId(funBD.get().getId());
		funAtualizar.setNome(funBD.get().getNome());
		funAtualizar.setSenha(funBD.get().getSenha());
		funAtualizar.setPerfil(funBD.get().getPerfil());
		
		if(!funBD.get().getNome().equals(funcionarioDto.getNome())){
			
			funAtualizar.setNome(funcionarioDto.getNome());
		}
		
		if((funcionarioDto.getSenha() != null && funcionarioDto.getSenha() != "")
				&& !funBD.get().getSenha().equals(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha()))) {
			
			funAtualizar.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha()));
		}
		
		if((funcionarioDto.getPerfil() !=null && funcionarioDto.getPerfil() != null) &&
				!funBD.get().getPerfil().toString().equals(funcionarioDto.getPerfil())) {
			
			if (EnumUtils.isValidEnum(PerfilEnum.class, funcionarioDto.getPerfil())) {
				funAtualizar.setPerfil(PerfilEnum.valueOf(funcionarioDto.getPerfil()));
			} else {
				result.addError(new ObjectError("perfil", "Perfil inválido."));
			}
			
		}
			
		return funAtualizar;
		
		
	}

}
