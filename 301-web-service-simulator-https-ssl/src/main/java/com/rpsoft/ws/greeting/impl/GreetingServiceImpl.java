package com.rpsoft.ws.greeting.impl;

import java.rmi.RemoteException;
import java.util.Set;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.rpsoft.ws.greeting.beans.GreetingMessage;
import com.rpsoft.ws.greeting.beans.PersonName;

@WebService(serviceName = "GreetingService", endpointInterface = "com.rpsoft.ws.greeting.GreetingServicePort")
@HandlerChain(file="handler/soapHandlers.xml")
public class GreetingServiceImpl implements SOAPHandler<SOAPMessageContext> {

	public GreetingMessage sendGreeting(PersonName personName) throws RemoteException {
		System.out.println("sendGreeting(PersonName)");
		
		final String greetingMsg = "Hello Mr. ";
		final String name = personName.getName();
		
		GreetingMessage greetingMessage = new GreetingMessage();
		greetingMessage.setMessage(greetingMsg.concat(name));
		
		return greetingMessage;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		System.out.println("handleMessage(SOAPMessageContext)");
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("handleFault(SOAPMessageContext)");
		return false;
	}

	@Override
	public void close(MessageContext context) {
		System.out.println("close(MessageContext)");
	}

	@Override
	public Set<QName> getHeaders() {
		System.out.println("getHeaders()");
		return null;
	}
}
