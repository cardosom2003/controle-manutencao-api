package com.cardoso.controlemanutencao.api.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cardoso.controlemanutencao.api.enums.TipoEnum;

@Entity
@Table(name= "equipamento")
public class Equipamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private TipoEnum tipo;
	private String marca;
	private String descricaoDefeito;
	private Cliente cliente;
	private List<OrdemServico> ordemServico;
	
	public Equipamento() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_equipamento", nullable = false)
	public TipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEnum tipo) {
		this.tipo = tipo;
	}
	
	@Column(name = "marca")
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Column(name ="descricao_defeito", nullable = false)
	public String getDescricaoDefeito() {
		return descricaoDefeito;
	}

	public void setDescricaoDefeito(String descricaoDefeito) {
		this.descricaoDefeito = descricaoDefeito;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id")
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@OneToMany(mappedBy = "equipamento", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<OrdemServico> getOrdemServico() {
		return ordemServico;
	}

	public void setOrdemServico(List<OrdemServico> ordemServico) {
		this.ordemServico = ordemServico;
	}

	@Override
	public String toString() {
		return "Equipamento [id=" + id + ", tipo=" + tipo + ", marca=" + marca + ", descricaoDefeito="
				+ descricaoDefeito + ", cliente=" + cliente + "]";
	}

	
}
