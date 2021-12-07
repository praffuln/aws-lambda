package com.amazonaws.lambda.dynamodb.lambdadynamodb;

import java.util.Collections;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
		"pathParameters": {
		    "id": "2b5159ea-1857-4e00-8197-967a2e9c8804"
		 }
 *
 *
 */
public class GetProductHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

		try {
			// get the 'pathParameters' from input
			Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
			String productId = pathParameters.get("id");

			// get the Product by id
			Product product = new Product().get(productId);

			// send the response back
			if (product != null) {
				// send the response back
				return new ApiGatewayResponse().setStatusCode("200").setBody(product.toString())
						.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));

			} else {

				// send the error response back
				return new ApiGatewayResponse().setStatusCode("404")
						.setBody("Product with id: '" + productId + "' not found.")
						.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));

			}
		} catch (Exception ex) {
			System.err.println("Error in retrieving product: " + ex);

			// send the error response back
			return new ApiGatewayResponse().setStatusCode("500").setBody("Error in retrieving product: " + ex)
					.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
		}
	}
}
