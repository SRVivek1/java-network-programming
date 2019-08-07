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
		
		HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 9119), 0);
		
		server.createContext("/test", new MyHandler());
		
		server.setExecutor(null); // creates a default executor
		
		server.start();
		
		System.out.println("Server is in running mode.");
		
		System.out.print("Press Enter to stop. >>");
		new Scanner(System.in).nextLine();
		server.stop(0);
		System.out.println("Server is stopped.");
	}

	/**
	 * Handler class to intercept calls.
	 * 
	 * @author vivek.ksingh
	 */
	static class MyHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String response = "This is the response";
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
