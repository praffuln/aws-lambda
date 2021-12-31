package com.git.lambda.restconsumer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        context.getLogger().log("Input: " + input);

        System.out.println("calling services ..........");
        NetClientGet.callHttpServiceFromEc2();
        System.out.println("called services ..........");
        
        // TODO: implement your handler
        return "Hello from Lambda!";
    }

}
