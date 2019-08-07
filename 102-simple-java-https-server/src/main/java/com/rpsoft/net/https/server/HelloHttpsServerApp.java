package com.rpsoft.net.https.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;

public class HelloHttpsServerApp {

	public static void main(String[] args) throws Exception {
		
		final int serverPort = 9120;
		final String sslProtocol = "TLS";
		final String keyStoreType = "JKS";
		final String keyStorePassword = "passw0rd";
		final String keyStoreFile = "dummyKeyStore.jks";
		try
		{
		    // setup the socket address
		    InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), serverPort);

		    // Initialize the HTTPS server
		    HttpsServer httpsServer = HttpsServer.create(inetSocketAddress, 0);
		    SSLContext sslContext = SSLContext.getInstance(sslProtocol);

		    // Initialize the KeyStore
		    char[] password = keyStorePassword.toCharArray();
		    KeyStore ks = KeyStore.getInstance(keyStoreType);
		    FileInputStream fis = new FileInputStream( "lig.keystore" );
		    ks.load ( fis, password );

		    // setup the key manager factory
		    KeyManagerFactory kmf = KeyManagerFactory.getInstance ( "SunX509" );
		    kmf.init ( ks, password );

		    // setup the trust manager factory
		    TrustManagerFactory tmf = TrustManagerFactory.getInstance ( "SunX509" );
		    tmf.init ( ks );

		    // setup the HTTPS context and parameters
		    sslContext.init ( kmf.getKeyManagers (), tmf.getTrustManagers (), null );
		    httpsServer.setHttpsConfigurator ( new HttpsConfigurator( sslContext )
		    {
		        public void configure ( HttpsParameters params )
		        {
		            try
		            {
		                // initialise the SSL context
		                SSLContext c = SSLContext.getDefault ();
		                SSLEngine engine = c.createSSLEngine ();
		                params.setNeedClientAuth ( false );
		                params.setCipherSuites ( engine.getEnabledCipherSuites () );
		                params.setProtocols ( engine.getEnabledProtocols () );

		                // get the default parameters
		                SSLParameters defaultSSLParameters = c.getDefaultSSLParameters ();
		                params.setSSLParameters ( defaultSSLParameters );
		            }
		            catch ( Exception ex )
		            {
		                //ILogger log = new LoggerFactory ().getLogger ();
		                //log.exception ( ex );
		                //log.error ( "Failed to create HTTPS port" );
		            }
		        }
		    } );
		    //LigServer server = new LigServer ( httpsServer );
		    //joinableThreadList.add ( server.getJoinableThread () );
		}
		catch ( Exception exception )
		{
		    //log.exception ( exception );
		    //log.error ( "Failed to create HTTPS server on port " + config.getHttpsPort () + " of localhost" );
		}
		
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
