package net.praffuln.handlers;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * https://www.rajanpanchal.net/lamda-layers-using-java-runtime/
 *
 */
public class HelloWorldHandler implements RequestHandler<Map<String,Object>, String>{
	  
	  public String handleRequest(Map<String,Object> event, Context context)
	  {
	    LambdaLogger logger = context.getLogger();
	    String response = new String("200 OK");
	    logger.log("ENVIRONMENT:"+System.getenv());
	    // log execution details
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
	    logger.log("CONTEXT: " + gson.toJson(context));
	    // process event
	    logger.log("EVENT: " + gson.toJson(event));
	    logger.log("EVENT TYPE: " + event.getClass().toString());
	    return response;
	  }
	}
