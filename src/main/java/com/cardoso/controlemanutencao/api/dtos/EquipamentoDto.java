package com.cardoso.controlemanutencao.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.cardoso.controlemanutencao.api.entities.Equipamento;

public class EquipamentoDto {
	
	private Long id;
	private String tipo;
	private String marca;
	private String descricaoDefeito;
	private Long clienteId;

	public EquipamentoDto() {
		
	}
	
	public EquipamentoDto(Equipamento obj) {
		
		id = obj.getId();
		tipo = obj.getTipo().toString();
		marca = obj.getMarca().toString();
		descricaoDefeito = obj.getDescricaoDefeito();
		clienteId = obj.getCliente().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@NotEmpty(message = "Marca não pode ser vazio.")
	@Length(min = 3, max = 50, message = "Nome deve conter entre 3 e 50 caracteres.")
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@NotEmpty(message = "Descricao não pode ser vazio.")
	@Length(min = 3, max = 255, message = "Nome deve conter entre 3 e 255 caracteres.")
	public String getDescricaoDefeito() {
		return descricaoDefeito;
	}

	public void setDescricaoDefeito(String descricaoDefeito) {
		this.descricaoDefeito = descricaoDefeito;
	}
	
	
	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	@Override
	public String toString() {
		return "EquipamentoDto [id=" + id + ", tipo=" + tipo + ", marca=" + marca + ", descricaoDefeito="
				+ descricaoDefeito + "]";
	}
	
	
	
	
}
