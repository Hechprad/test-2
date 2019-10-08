package br.com.sciensa.corujaoapi.model;

import java.util.List;

import javax.validation.Valid;

public class ResponseError {

	private Integer code = null;

	private String type = null;

	@Valid
	private List<String> messages = null;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
