package com.test.process.try_process_automatec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.microsoft.graph.models.User;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		String commandExe = "C:\\Users\\SESA649079\\Desktop\\try me\\Blue Prism Automate\\AutomateC.exe";
		
		List<String> command = new ArrayList<String>();
		command.add(commandExe);
		command.add("/sso");
		command.add("/dbconname");
		command.add("Connection 2");
		command.add("/run");
		command.add("prafful-notepad");
		command.add("/resource");
		command.add("WRPARCLT1000DEV");
		
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.redirectErrorStream(true);
		Process process;
		 		  process = pb.start();
		  process.waitFor();
		  if (process.exitValue() == 0) {
		    // success
				BufferedReader stdInput = new BufferedReader(new 
					     InputStreamReader(process.getInputStream()));

					BufferedReader stdError = new BufferedReader(new 
					     InputStreamReader(process.getErrorStream()));

					// Read the output from the command
					System.out.println("Here is the standard output of the command:\n");
					String s = null;
					while ((s = stdInput.readLine()) != null) {
					    System.out.println(s);
					}

					// Read any errors from the attempted command
					System.out.println("Here is the standard error of the command (if any):\n");
					while ((s = stdError.readLine()) != null) {
					    System.out.println(s);
					}
		  } else {
		    // failure
			  System.out.println("failed process "+process.exitValue());
		  }
		}  
}
 
