package com.amazonaws.lambda.dynamodb.lambdadynamodb.handler;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.amazonaws.lambda.dynamodb.lambdadynamodb.ApiGatewayResponse;
import com.amazonaws.lambda.dynamodb.lambdadynamodb.Product;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ListProductsHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {


	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
    try {
        // get all products
        List<Product> products = new Product().list();

        // send the response back
		// send the response back
		return new ApiGatewayResponse().setStatusCode("200").setBody(String.valueOf(products.size()))
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));

    } catch (Exception ex) {
		// send the error response back
		return new ApiGatewayResponse().setStatusCode("500").setBody("Error in saving product: " + input)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & Serverless"));
    }
  }
}
