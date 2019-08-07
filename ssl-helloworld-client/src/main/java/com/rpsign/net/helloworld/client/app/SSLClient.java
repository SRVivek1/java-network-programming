package com.rpsign.net.helloworld.client.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClient {

	public static void main(String[] args) {
		
		final String trustStoreFile = "C:\\Windows\\SystemD\\WorkSpaces\\workspace\\java-network-programming\\ssl-helloworld-client\\src\\main\\resource\\com\\rpsing\\net\\truststore\\localtruststore.ts";
		final String trustStorePass = "passw0rd";
		try
	    {
			System.setProperty("javax.net.ssl.trustStore", trustStoreFile);
			System.setProperty("javax.net.ssl.trustStorePassword", trustStorePass);
			
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
			SSLSocket sslsocket = (SSLSocket)sslsocketfactory.createSocket("localhost", 54321);
			
			InputStream inputstream = sslsocket.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
			//OutputStream outputstream = sslsocket.getOutputStream();
			//OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
			//BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
			String string = null;
			while ((string = bufferedreader.readLine()) != null)
			{
				//bufferedwriter.write(string + '\n');
				//bufferedwriter.flush();
				System.out.println("response"+string);
			}
	    }
	    catch (Exception exception)
	    {
	    	exception.printStackTrace();
	    }

	}

}
