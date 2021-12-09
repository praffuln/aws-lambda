package com.handy.aws.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

public class InventoryFindFunction implements RequestHandler<Map<String,Object>, String> {

    @Override
    public String handleRequest(Map<String,Object> input, Context context) {
        context.getLogger().log("Input: " + input);

        Region region = Region.US_WEST_2;
        //create s3client object
        S3Client s3Client = S3Client.builder().region(region).build();
        
        //get object
        ResponseInputStream<GetObjectResponse> objectData = 
        		s3Client.getObject(GetObjectRequest.builder().bucket("handy-inventory-data-123p")
        				.key("s3testdata.txt").build());
        
       //read objectData inside bufferedReader
        InputStreamReader isr = new InputStreamReader(objectData);
        BufferedReader br = new BufferedReader(isr);
         
        String outputString = null;
        
        try {
			outputString = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return outputString;
    }
}
