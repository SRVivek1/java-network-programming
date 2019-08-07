package com.rpsoft.ws.greeting.server;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.ws.Binding;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;

import com.rpsoft.ws.greeting.impl.GreetingServiceImpl;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

class GreetingSimulatorServer {

	public static void main(String[] args) throws Exception {

		final String keystoreFile = "C:\\Windows\\SystemD\\WorkSpaces\\workspace\\java-network-programming\\301-web-service-simulator-https-ssl\\src\\main\\resource\\keystore\\localkeystore.jks";
		
		final String keystoreType = "JKS";
		
		final String keystorePass = "passw0rd";
		
		String serviceURL = "https://localhost:9119/ws/greeting";
		
		final String uri = "/ws/greeting";

		final int port = 9119;
		
		final String HTTP_ADDRESS = "http://10.10.10.12:9120/ws/greeting";
		
		KeyStore keyStore = KeyStore.getInstance(keystoreType);
		keyStore.load(new FileInputStream(keystoreFile), keystorePass.toCharArray());

		KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyFactory.init(keyStore, keystorePass.toCharArray());

		TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustFactory.init(keyStore);

		final SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(keyFactory.getKeyManagers(), trustFactory.getTrustManagers(), new SecureRandom());

		final HttpsConfigurator configurator = new HttpsConfigurator(sslContext);

		final HttpsServer httpsServer = HttpsServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), port), port);
		httpsServer.setHttpsConfigurator(configurator);

		final HttpContext httpContext = httpsServer.createContext(uri);

		httpsServer.start();

		final GreetingServiceImpl serviceImpl = new GreetingServiceImpl();
		
		// HTTPS
		Endpoint endpoint = null;
		endpoint = Endpoint.create(serviceImpl);
		endpoint.publish(httpContext);
		
		//HTTP
		//Endpoint.publish(HTTP_ADDRESS, serviceImpl);
		
		Binding binding = endpoint.getBinding();
		List<Handler> handerChainList = binding.getHandlerChain();
		handerChainList.add(serviceImpl);
		binding.setHandlerChain(handerChainList);

		if (endpoint.isPublished()) {
			System.out.println("***WebService started.");
			System.out.println("Service running on : " + serviceURL);
		}
		
		boolean run = true;
		Scanner scanner = new Scanner(System.in);
		while (run) {
			System.out.print("Input command (stop) : ");
			String command = scanner.next();
			if (command.equalsIgnoreCase("stop")) {
				System.out.println("Shutting down webservice hosting.");
				endpoint.stop();
				run = false;
			} else {
				System.out.println("Inavlid command.");
			}
		}
		scanner.close();
	}

}
