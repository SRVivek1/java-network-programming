package com.rpsoft.ws.ntlm.client.app;

import org.bhoopen.ntlm.ws.client.GreetingServiceImplPortBinding;
import org.bhoopen.ntlm.ws.client.PersonName;
import org.bhoopen.ntlm.ws.client.Service1;

public class ClientApp {

	public static void main(String[] args) {

		final Service1 service1 = new Service1();
		
		final String response1 = service1.getService1Soap().helloWorld();
		//System.out.println("Response-1 Received : " + response1);
		
		final GreetingServiceImplPortBinding greetingService = service1.getGreetingServiceImplPortBinding();
		PersonName person = new PersonName();
		person.setName("User");
		System.out.println("Greeting Service : " + greetingService.sendGreeting(person).getMessage());
		;
	}

}
