package com.rpsoft.ws.greeting.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SoapMessageHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		System.out.println("handleMessage(SOAPMessageContext)");
		Boolean outBoundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

		if (!outBoundProperty) {
			System.out.println("Soap Request : ");
		} else {
			System.out.println("Soap Response : ");
		}

		final ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
		String soapMessage = null;
		try {
			context.getMessage().writeTo(bOutputStream);
			soapMessage = bOutputStream.toString();
		} catch (SOAPException | IOException e) {
			e.printStackTrace();
		}

		System.out.println(prettyFormat(soapMessage, 2));

		return Boolean.TRUE;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("handleFault(SOAPMessageContext)");
		return true;
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

	/*
	 * This method formats xml string
	 */
	public static String prettyFormat(String input, int indent) {
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setAttribute("indent-number", indent);
		
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		final Source xmlInput = new StreamSource(new StringReader(input));
		final StringWriter stringWriter = new StringWriter();
		final StreamResult xmlOutput = new StreamResult(stringWriter);
		
		try {
			transformer.transform(xmlInput, xmlOutput);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return xmlOutput.getWriter().toString();

	}
}
