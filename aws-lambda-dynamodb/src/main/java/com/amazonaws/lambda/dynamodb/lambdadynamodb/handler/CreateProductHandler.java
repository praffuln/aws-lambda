package com.amazonaws.lambda.dynamodb.lambdadynamodb.handler;

import java.util.Collections;
import java.util.Map;

import com.amazonaws.lambda.dynamodb.lambdadynamodb.ApiGatewayResponse;
import com.amazonaws.lambda.dynamodb.lambdadynamodb.Product;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * api gateway json as below 
 * 
 *  "body": "{\"name\": \"PRODUCT-2\",\"price\": 22.3}"
 *
 */
public class CreateProductHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

		try {
			// get the 'body' from input
			JsonNode body = new ObjectMapper().readTree((String) input.get("body"));

			// create the Product object for post
			Product product = new Product();
			// product.setId(body.get("id").asText());
			product.setName(body.get("name").asText());
			product.setPrice((float) body.get("price").asDouble());
			product.save(product);

			// send the response back
			return new ApiGatewayResponse().setStatusCode("200").setBody(product.toString())
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));

		} catch (Exception ex) {
			System.err.println("Error in saving product: " + ex);

			// send the error response back
			return new ApiGatewayResponse().setStatusCode("500").setBody("Error in saving product: " + input)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
		}
	}
}
