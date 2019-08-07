/**
 *  :: Rest Server ::
 * ====================
 */
package com.rpsoft.rest.ws.simulator.server;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import com.rpsoft.rest.ws.simulator.server.impl.Calculator;

/**
 * In order to deploy our WebService we need a ServletContainer for which we
 * will use Jersey and a WebServer into which we can drop the container for
 * which we will use Jetty.
 * 
 * @author vivek.ksingh
 */
public class RestServerSSLApp {

	private static final String KEY_STORE_PATH = new StringBuffer(System.getProperty("user.dir"))
			.append("\\src\\main\\resources\\keystore\\localkeystore.jks").toString();

	private static final String KEY_STORE_PASS = "passw0rd";

	private static final String CONTECT_PATH = "/";

	private static final String CONTAINER_PATH = "/*";

	private static final String SERVER_PROVIDER_CLASSES = "jersey.config.server.provider.classnames";

	private static final int initOrder = 0;

	private static final String HTTPS = "https";

	private static final int HTTP_PORT = 3020;

	private static final int HTTPS_PORT = 3021;

	private static final int HTTP_BUFFER_SIZE = 32768;

	private static final int HTTP_TIMEOUT = 30000;

	private static final int HTTPS_TIMEOUT = 500000;

	/**
	 * Start server application.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		if (!new File(KEY_STORE_PATH).exists()) {
			throw new FileNotFoundException(KEY_STORE_PATH);
		}

		// Basic jetty server object without declaring the port. Since
		// we are configuring connectors directly we'll be setting ports on
		// those connectors.
		final Server jettyServer = new Server();

		// HttpConfiguration is a collection of configuration information
		// appropriate for http and https. The default scheme for http is
		// <code>http</code> of course, as the default for secured http is
		// <code>https</code> but we show setting the scheme to show it can be
		// done. The port for secured communication is also set here.
		final HttpConfiguration http_config = new HttpConfiguration();
		http_config.setSecureScheme(HTTPS);
		http_config.setSecurePort(HTTPS_PORT);
		http_config.setOutputBufferSize(HTTP_BUFFER_SIZE);

		// HTTP connector
		// The first server connector we create is the one for http, passing in
		// the http configuration we configured above so it can get things like
		// the output buffer size, etc. We also set the port (8080) and
		// configure an idle timeout.
		final ServerConnector serverConnectorHttp = new ServerConnector(jettyServer,
				new HttpConnectionFactory(http_config));
		serverConnectorHttp.setPort(HTTP_PORT);
		serverConnectorHttp.setIdleTimeout(HTTP_TIMEOUT);

		// SSL Context Factory for HTTPS
		// SSL requires a certificate so we configure a factory for ssl contents
		// with information pointing to what keystore the ssl connection needs
		// to know about. Much more configuration is available the ssl context,
		// including things like choosing the particular certificate out of a
		// keystore to be used.
		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setKeyStorePath(KEY_STORE_PATH);
		sslContextFactory.setKeyStorePassword(KEY_STORE_PASS);
		sslContextFactory.setKeyManagerPassword(KEY_STORE_PASS);

		// HTTPS Configuration
		// A new HttpConfiguration object is needed for the next connector and
		// you can pass the old one as an argument to effectively clone the
		// contents. On this HttpConfiguration object we add a
		// SecureRequestCustomizer which is how a new connector is able to
		// resolve the https connection before handing control over to the Jetty
		// Server.
		final SecureRequestCustomizer src = new SecureRequestCustomizer();
		// src.setStsMaxAge(2000);
		// src.setStsIncludeSubDomains(true);

		final HttpConfiguration https_config = new HttpConfiguration(http_config);
		https_config.addCustomizer(src);

		// HTTPS connector
		// We create a second ServerConnector, passing in the http configuration
		// we just made along with the previously created ssl context factory.
		// Next we set the port and a longer idle timeout.
		final ServerConnector serverConnectorHttps = new ServerConnector(jettyServer,
				new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
				new HttpConnectionFactory(https_config));
		serverConnectorHttps.setPort(HTTPS_PORT);
		serverConnectorHttps.setIdleTimeout(HTTPS_TIMEOUT);

		// Set the connectors
		// Here you see the server having multiple connectors registered with
		// it, now requests can flow into the server from both http and https
		// urls to their respective ports and be processed accordingly by jetty.
		// A simple handler is also registered with the server so the example
		// has something to pass requests off to.
		jettyServer.setConnectors(new Connector[] { serverConnectorHttp, serverConnectorHttps });

		final ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContextHandler.setContextPath(CONTECT_PATH);
		// Set a handler
		jettyServer.setHandler(servletContextHandler);

		final ServletHolder jerseyServletHolder = servletContextHandler
				.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, CONTAINER_PATH);
		jerseyServletHolder.setInitOrder(initOrder);
		jerseyServletHolder.setInitParameter(SERVER_PROVIDER_CLASSES, Calculator.class.getCanonicalName());

		// Start the server
		try {
			jettyServer.start();
			jettyServer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jettyServer.destroy();
		}
	}
}