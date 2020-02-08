package com.cardoso.controlemanutencao.api.dtos;

import com.cardoso.controlemanutencao.api.entities.OrdemServico;

public class OrdemServicoDto {

	private Long id;
	private Long equipamentoId;
	private Long funcionarioId;
	private String ordemStatus;
	private String dataCriacao;
	private String inicioServico;
	private String conclusaoServico;
	private String notaConclusao;
	
	public OrdemServicoDto() {
		
	}
	
	public OrdemServicoDto(OrdemServico obj) {
		
		id = obj.getId();
		equipamentoId = obj.getEquipamento().getId();
		funcionarioId = obj.getFuncionario().getId();
		ordemStatus = obj.getOrdemStatus().toString();
		dataCriacao = obj.getDataCriacao().toString();
		if(obj.getInicioServico() != null) {
			inicioServico = obj.getInicioServico().toString();
		}
		if(obj.getConclusaoServico() !=null) {
			conclusaoServico = obj.getConclusaoServico().toString();
		}
		notaConclusao = obj.getNotaConclusao();
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEquipamentoId() {
		return equipamentoId;
	}

	public void setEquipamentoId(Long equipamentoId) {
		this.equipamentoId = equipamentoId;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getOrdemStatus() {
		return ordemStatus;
	}

	public void setOrdemStatus(String ordemStatus) {
		this.ordemStatus = ordemStatus;
	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getInicioServico() {
		return inicioServico;
	}

	public void setInicioServico(String inicioServico) {
		this.inicioServico = inicioServico;
	}

	public String getConclusaoServico() {
		return conclusaoServico;
	}

	public void setConclusaoServico(String conclusaoServico) {
		this.conclusaoServico = conclusaoServico;
	}

	public String getNotaConclusao() {
		return notaConclusao;
	}

	public void setNotaConclusao(String notaConclusao) {
		this.notaConclusao = notaConclusao;
	}

	@Override
	public String toString() {
		return "OrdemServicoDto [id=" + id + ", equipamentoId=" + equipamentoId + ", funcionarioId=" + funcionarioId
				+ ", ordemStatus=" + ordemStatus + ", dataCriacao=" + dataCriacao + ", inicioServico=" + inicioServico
				+ ", conclusaoServico=" + conclusaoServico + ", notaConclusao=" + notaConclusao + "]";
	}
	
	
	

}
