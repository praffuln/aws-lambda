package com.amazonaws.lambda.dynamodb.lambdadynamodb.handler;

import java.util.Collections;
import java.util.Map;

import com.amazonaws.lambda.dynamodb.lambdadynamodb.ApiGatewayResponse;
import com.amazonaws.lambda.dynamodb.lambdadynamodb.Product;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteProductHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

		try {
			// get the 'pathParameters' from input
			Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
			String productId = pathParameters.get("id");

			// get the Product by id
			Boolean success = new Product().delete(productId);

			// send the response back
			if (success) {
				return new ApiGatewayResponse().setStatusCode("204").setBody("" + success)
						.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));

			} else {
				// send the error response back
				return new ApiGatewayResponse().setStatusCode("404")
						.setBody("Product with id: '" + productId + "' not found.")
						.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
			}
		} catch (Exception ex) {
			System.err.println("Error in deleting product: " + ex);

			// send the error response back
			return new ApiGatewayResponse().setStatusCode("500").setBody("Error in deleting product: " + ex)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
		}
	}
}
