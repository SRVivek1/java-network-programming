package com.rpsoft.ws.greeting.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

/**
 * This class configures https and ssl details for server
 * 
 * @author vivek.ksingh
 *
 */
public class HttpsSSLConfigurator {

	private static final String keystoreFile = "C:\\Windows\\SystemD\\WorkSpaces\\workspace\\java-network-programming\\301-web-service-simulator-https-ssl\\src\\main\\resource\\keystore\\localkeystore.jks";

	private static final String keystoreType = "JKS";

	private static final String keystorePass = "passw0rd";

	private static final int port = 9119;

	private static final String uri1 = "/ws/greeting";
	private static final String uri2 = "/ws/welcome";
	private static final String uri3 = "/ws/hello";

	public static HttpContext[] configureHttpsSSL() throws NoSuchAlgorithmException, CertificateException,
			FileNotFoundException, IOException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {

		KeyStore keyStore = KeyStore.getInstance(keystoreType);
		keyStore.load(new FileInputStream(keystoreFile), keystorePass.toCharArray());

		KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		keyFactory.init(keyStore, keystorePass.toCharArray());

		TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustFactory.init(keyStore);

		final SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(keyFactory.getKeyManagers(), trustFactory.getTrustManagers(), new SecureRandom());

		final HttpsConfigurator configurator = new HttpsConfigurator(sslContext);

		final HttpsServer httpsServer = HttpsServer
				.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), port), port);
		httpsServer.setHttpsConfigurator(configurator);

		final HttpContext httpContext1 = httpsServer.createContext(uri1);
		final HttpContext httpContext2 = httpsServer.createContext(uri2);
		final HttpContext httpContext3 = httpsServer.createContext(uri3);

		final HttpContext[] httpContexts = new HttpContext[3];
		httpContexts[0] =  httpContext1;
		httpContexts[1] =  httpContext2;
		httpContexts[2] =  httpContext3;
		
		httpsServer.start();

		return httpContexts;
	}
}
