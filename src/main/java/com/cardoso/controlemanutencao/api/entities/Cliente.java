package com.cardoso.controlemanutencao.api.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String endereco;
	private String email;
	private String telefone;
	private List<Equipamento> equipamento;
	
	public Cliente() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name= "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	@Column(name = "endereco", nullable = false)
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Column(name ="telefone", nullable = false)
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Equipamento> getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(List<Equipamento> equipamento) {
		this.equipamento = equipamento;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", email=" + email + ", telefone="
				+ telefone + "]";
	}

	
}
