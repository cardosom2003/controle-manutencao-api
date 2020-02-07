package com.cardoso.controlemanutencao.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.cardoso.controlemanutencao.api.entities.Funcionario;

public class FuncionarioDto {
	
	private Long id;
	private String nome;
	private String senha;
	private String perfil;
	
	
	public FuncionarioDto() {
		
	}
	
	
	public FuncionarioDto(Funcionario obj) {
		
		id = obj.getId();
		nome = obj.getNome();
		senha = obj.getSenha();
		perfil = obj.getPerfil().toString();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 100, message = "Nome deve conter entre 3 e 100 caracteres.")
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	
	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}

	
	public String getPerfil() {
		return perfil;
	}


	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}


	@Override
	public String toString() {
		return "FuncionarioDto [id=" + id + ", nome=" + nome + ", senha=" + senha + ", perfil=" + perfil + "]";
	}
	
	
	
}
