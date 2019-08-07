package com.rpsoft.net.http.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HelloHttpServerApp {

	public static void main(String[] args) throws Exception {
		
		// Bind server instance with host and port
		HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 9119), 0);
		
		// Create multiple contexts and supply their required handlers
		server.createContext("/hone", new MyHandlerOne());
		server.createContext("/htwo", new MyHandlerTwo());
		
		// Empty context
		server.createContext("/hthree");
		
		server.setExecutor(null); // creates a default executor
		
		// Start server
		server.start();
		
		System.out.println("Server is in running mode.");
		
		System.out.print("Press Enter to stop. >>");
		final Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
		
		// Stops server
		server.stop(0);
		
		System.out.println("Server is stopped.");
	}

	/**
	 * Handler class to intercept calls.
	 * 
	 * @author vivek.ksingh
	 */
	static class MyHandlerOne implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String response = "Response from MyHandlerOne.";
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
	
	/**
	 * Handler class to intercept calls.
	 * 
	 * @author vivek.ksingh
	 */
	static class MyHandlerTwo implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String response = "Response from MyHandlerTwo.";
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
