package com.timedeal.common.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	private int statusCode;
	private String errorContent;
	private List<String> messages;

	public ErrorResponse(HttpStatus statusCode, String errorContent, String messages) {
		this.statusCode = statusCode.value();
		this.errorContent = errorContent;
		this.messages = new ArrayList<>();
		this.messages.add(messages);
	}

	public ErrorResponse(HttpStatus statusCode, String errorContent, List<String> messages) {
		this.statusCode = statusCode.value();
		this.errorContent = errorContent;
		this.messages = messages;
	}
}