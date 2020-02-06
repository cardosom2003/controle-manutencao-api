package com.cardoso.controlemanutencao.api.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
	
	private T data;
	private List<String> errors;
	
	public Response() {
		
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getErros() {
		
		if(this.errors == null) {
			this.errors = new ArrayList<String>();
		}
		return errors;
	}

	public void setErros(List<String> erros) {
		this.errors = erros;
	}
	
	
}