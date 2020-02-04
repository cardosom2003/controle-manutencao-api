package com.cardoso.controlemanutencao.api.entities;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.cardoso.controlemanutencao.api.enums.StatusEnum;

@Entity
@Table(name = "ordemservico")
public class OrdemServico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Equipamento equipamento;
	private Funcionario funcionario;
	private StatusEnum ordemStatus;
	private Date dataCriacao;
	private Date inicioServico;
	private Date conclusaoServico;
	private String notaConclusao;
	
	public OrdemServico() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "equipamento_id")
	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "funcionario_id")
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name= "ordem_status", nullable = false)
	public StatusEnum getOrdemStatus() {
		return ordemStatus;
	}

	public void setOrdemStatus(StatusEnum ordemStatus) {
		this.ordemStatus = ordemStatus;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name= "data_criacao", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Column(name = "data_inicio")
	public Date getInicioServico() {
		return inicioServico;
	}

	public void setInicioServico(Date inicioServico) {
		this.inicioServico = inicioServico;
	}
	
	@Column(name = "data_conclusao")
	public Date getConclusaoServico() {
		return conclusaoServico;
	}

	public void setConclusaoServico(Date conclusaoServico) {
		this.conclusaoServico = conclusaoServico;
	}
	
	@Column(name = "nota_conclusao")
	public String getNotaConclusao() {
		return notaConclusao;
	}

	public void setNotaConclusao(String notaConclusao) {
		this.notaConclusao = notaConclusao;
	}
	
	@PrePersist
	public void prePersist() {
		final Date dataAtual = new Date();
		dataCriacao = dataAtual;
	}

	@Override
	public String toString() {
		return "OrdemServico [id=" + id + ", equipamento=" + equipamento + ", funcionario=" + funcionario
				+ ", ordemStatus=" + ordemStatus + ", dataCriacao=" + dataCriacao + ", inicioServico=" + inicioServico
				+ ", conclusaoServico=" + conclusaoServico + ", notaConclusao=" + notaConclusao + "]";
	}
	
	
}
