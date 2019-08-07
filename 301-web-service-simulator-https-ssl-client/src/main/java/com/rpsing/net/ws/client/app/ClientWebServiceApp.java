package com.rpsing.net.ws.client.app;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.ws.BindingProvider;

import com.rpsing.net.ws.client.stub.GreetingException_Exception;
import com.rpsing.net.ws.client.stub.GreetingMessage;
import com.rpsing.net.ws.client.stub.GreetingService;
import com.rpsing.net.ws.client.stub.GreetingServicePort;
import com.rpsing.net.ws.client.stub.PersonName;

public class ClientWebServiceApp {

	public static void main(String[] args) throws GreetingException_Exception {
		
		final String keyStorePath = "C:\\Windows\\SystemD\\WorkSpaces\\workspace\\java-network-programming\\301-web-service-simulator-https-ssl-client\\src\\main\\resource\\truststore\\localtruststore.ts";
		final String dummyTrustStore = "C:\\Windows\\SystemD\\WorkSpaces\\workspace\\java-network-programming\\301-web-service-simulator-https-ssl-client\\src\\test\\resources\\keystore\\dummytruststore.ts";
		
		final String trustStorePass = "passw0rd";
		
		final String keyStorePass = "passw0rd";
		
		final String keyStoreType = "JKS";
		
		System.setProperty("javax.net.ssl.trustStore", dummyTrustStore);
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePass);
		
		/*
		 * Not required
		 */
		/*System.setProperty("javax.net.ssl.keyStore", keyStorePass);
		System.setProperty("javax.net.ssl.keyStorePassword", keyStorePass);
		System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);*/

		/**
		 * No impact
		 */
		// done to prevent CN verification in client keystore
		/*HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});*/

		
		//WebService
		
		final GreetingService serviceClient = new GreetingService();
		final GreetingServicePort servicePort = serviceClient.getGreetingServiceSOAP();
		
		String endpointURL = "https://localhost:9119/ws/greeting";
		
		BindingProvider bindingProvider = (BindingProvider)servicePort;
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);
		
		PersonName personName = new PersonName();
		personName.setName("Ramu");
		GreetingMessage response = servicePort.sendGreeting(personName);
		
		System.out.println("Message received : " + response.getMessage());
	}

}
