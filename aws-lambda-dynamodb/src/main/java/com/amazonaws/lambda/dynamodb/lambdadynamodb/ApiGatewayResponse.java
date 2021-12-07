package com.amazonaws.lambda.dynamodb.lambdadynamodb;

import java.util.HashMap;
import java.util.Map;

public class ApiGatewayResponse {
	private String body;
	private String statusCode = "200";
	private Map<String, String> headers = new HashMap<>();

	public ApiGatewayResponse() {
		this.headers.put("Content-type", "application/json");
	}

	public String getBody() {
		return body;
	}

	public ApiGatewayResponse setBody(String body) {
		this.body = body;
		return this;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public ApiGatewayResponse setStatusCode(String statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public ApiGatewayResponse setHeaders(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

}
