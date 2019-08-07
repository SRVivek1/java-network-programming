package com.rpsign.net.helloworld.server.app;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLServerSocketApp {

	public static void main(String[] args) {

		/* Type of KeyStore file we have. */
		final String keyStoreType = "jks";

		/* This file is provided in class path. */
		final String keyStoreFile = "C:\\Windows\\SystemD\\WorkSpaces\\workspace\\java-network-programming\\ssl-helloworld-server\\src\\main\\resource\\com\\rpsign\\net\\helloworld\\server\\app\\localkeystore.jks";

		/* KeyStore password. */
		final String keyStorePass = "passw0rd";
		final char[] keyStorePassArray = keyStorePass.toCharArray();

		/* Alias in the KeyStore. */
		//final String alias = "localserver";

		/* SSL protocol to be used. */
		final String sslProtocol = "TLS";

		/* Server port. */
		final int serverPort = 54321;
		try {
			/*
			 * Prepare KeyStore instance for the type of KeyStore file we have.
			 */
			final KeyStore keyStore = KeyStore.getInstance(keyStoreType);

			/* Load the keyStore file */
			//final InputStream keyStoreFileIStream = SSLServerSocketApp.class.getResourceAsStream(keyStoreFile);
			final InputStream keyStoreFileIStream = new FileInputStream(keyStoreFile);
			keyStore.load(keyStoreFileIStream, keyStorePassArray);

			/* Get the Private Key associated with given alias. */
			//final Key key = keyStore.getKey(alias, keyStorePassArray);

			/*  */
			final String algorithm = KeyManagerFactory.getDefaultAlgorithm();
			final KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(algorithm);
			keyManagerFactory.init(keyStore, keyStorePassArray);

			// Prepare SSL
			final SSLContext sslContext = SSLContext.getInstance(sslProtocol);
			sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

			/* Server program to host itself on a specified port. */
			SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
			ServerSocket serverSocket = sslServerSocketFactory.createServerSocket(serverPort);

			while (true) {
				System.out.print("Listening on " + serverPort + ">>>");
				Socket s = serverSocket.accept();
				PrintStream out = new PrintStream(s.getOutputStream());
				out.println("Hi");
				/*System.out.println("HI");*/
				out.close();
				s.close();
				System.out.println("Connection closed.");
			}

			// ---------------------------------
			/*
			 * String keystorePasswordCharArray="password";
			 * 
			 * KeyStore ks = KeyStore.getInstance("JKS"); InputStream readStream
			 * = new FileInputStream(
			 * "C:\\Users\\sandeshd\\Desktop\\Wk\\SSLServerSocket\\keystore\\keystore.jks"
			 * ); ks.load(readStream, keystorePasswordCharArray.toCharArray());
			 * Key key = ks.getKey("serversocket", "password".toCharArray());
			 * 
			 * SSLContext context = SSLContext.getInstance("TLS");
			 * KeyManagerFactory kmf= KeyManagerFactory.getInstance("SunX509");
			 * kmf.init(ks, "password".toCharArray());
			 * context.init(kmf.getKeyManagers(), null, null);
			 * SSLServerSocketFactory ssf = context.getServerSocketFactory();
			 * 
			 * ServerSocket ss = ssf.createServerSocket(5432); while (true) {
			 * Socket s = ss.accept(); PrintStream out = new
			 * PrintStream(s.getOutputStream()); out.println("Hi"); out.close();
			 * s.close(); }
			 */
		} catch (Exception e) {
			//System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}
