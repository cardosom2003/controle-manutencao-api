package com.cardoso.controlemanutencao.api.dtos;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.cardoso.controlemanutencao.api.entities.Cliente;

public class ClienteDto {
	
	private Long id;
	private String nome;
	private String endereco;
	private String email;
	private String telefone;
	
	public ClienteDto() {
		
	}
	
	public ClienteDto(Cliente obj) {
		
		id = obj.getId();
		nome = obj.getNome();
		endereco = obj.getEndereco();
		email = obj.getEmail();
		telefone = obj.getTelefone();
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
	
	@NotEmpty(message = "Endereço não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Endereço deve conter entre 3 e 200 caracteres.")
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@NotEmpty(message = "Email não pode ser vazio.")
	@Email
	@Length(min = 3, max = 60, message = "Email deve conter entre 3 e 60 caracteres.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@NotEmpty(message = "Telefone não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Telefone deve conter entre 3 e  caracteres.")
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	
	@Override
	public String toString() {
		return "ClienteDto [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", email=" + email + ", telefone="
				+ telefone + "]";
	}
	
	

}
