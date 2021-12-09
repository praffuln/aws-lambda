package com.handy.aws.functions;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public class InventoryFindFunction2 implements RequestHandler<HttpQuerystringRequest, HttpProductResponse> {

	@Override
	public HttpProductResponse handleRequest(HttpQuerystringRequest request, Context context) {
		context.getLogger().log("Input: " + request);
		String idAsString = (String) request.getQueryStringParameters().get("id");
		Integer idAsInt = Integer.parseInt(idAsString);
		
		//this is for test 
		Product product = new Product();
		product.setBrand("test");product.setCount(2);product.setId(2);product.setName("testName");
		product.setToolType("testToolType");
//		return new HttpProductResponse(getProduct(idAsInt));
		
		return new HttpProductResponse(product);
	}
 
	private Product getProduct(int index) {
		
		Region region = Region.US_WEST_2;
		// create s3client object
		S3Client s3Client = S3Client.builder().region(region).build();

		// get object
		ResponseInputStream<GetObjectResponse> objectData = s3Client.getObject(
				GetObjectRequest.builder().bucket("handy-inventory-data-123p")
				.key("handy-tool-catalog.json").build());

		// read objectData inside bufferedReader
		InputStreamReader isr = new InputStreamReader(objectData);
		BufferedReader br = new BufferedReader(isr);
		
		Product[] products = null;
		Gson gson = new Gson();
		products = gson.fromJson(br, Product[].class);
		// TODO: implement your handler
		return products[index];
	}

}