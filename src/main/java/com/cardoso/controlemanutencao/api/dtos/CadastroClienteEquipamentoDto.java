package com.cardoso.controlemanutencao.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.cardoso.controlemanutencao.api.enums.TipoEnum;

public class CadastroClienteEquipamentoDto {
	
	
	private Long id;
	// Dados  referente ao Cliente
	private String nome;
	private String endereco;
	private String email;
	private String telefone;
	
	// Dados referente ao Equipamento
	private String tipo;
	private String marca;
	private String descricaoDefeito;
	
	public CadastroClienteEquipamentoDto() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	 
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	@NotEmpty(message = "Endereço não pode ser vazio.")
	@Length(min= 3, max = 255, message = "Endereço deve conter entre 3 e 255 caracteres.")
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@NotEmpty(message = "E-mail não pode ser vazio.")
	@Length(min= 3, max = 200, message = "E-mail deve conter entre 3 e 200 caracteres.")
	@Email(message = "E-mail inválido")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@NotEmpty(message = "Telefone não pode ser vazio.")
	@Length(min= 3, max = 20, message = "Telefone deve conter entre 3 e 20 caracteres.")
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@NotEmpty(message = "Marca não pode ser vazio.")
	@Length(min= 3, max = 20, message = "Marca deve conter entre 3 e 20 caracteres.")
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	@NotEmpty(message = "Descricao não pode ser vazio.")
	@Length(min= 3, max = 255, message = "Marca deve conter entre 3 e 255 caracteres.")
	public String getDescricaoDefeito() {
		return descricaoDefeito;
	}

	public void setDescricaoDefeito(String descricaoDefeito) {
		this.descricaoDefeito = descricaoDefeito;
	}

	@Override
	public String toString() {
		return "CadastroClienteEquipamentoDto [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", email="
				+ email + ", telefone=" + telefone + ", tipo=" + tipo + ", marca=" + marca + ", descricaoDefeito="
				+ descricaoDefeito + "]";
	}

	
	
}
