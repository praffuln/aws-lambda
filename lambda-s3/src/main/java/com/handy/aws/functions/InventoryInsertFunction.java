package com.handy.aws.functions;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class InventoryInsertFunction extends InventoryS3Client 
	implements RequestHandler<HttpRequest, HttpProductResponse> {

	@Override
	public HttpProductResponse handleRequest(HttpRequest request, Context context) {
		context.getLogger().log("Input: " + request);

		String body = request.getBody();

		context.getLogger().log("Body is : " + body);
		
		Gson gson = new Gson();
		Product productToAdd = gson.fromJson(body, Product.class);

		List<Product> productsList = getAllProductsList();
		productsList.add(productToAdd);

		if (super.updateAllProducts(productsList)) {
			return new HttpProductResponse();
		}

		HttpProductResponse response = new HttpProductResponse();
		response.setStatusCode("500");
		return response;
	}

}
