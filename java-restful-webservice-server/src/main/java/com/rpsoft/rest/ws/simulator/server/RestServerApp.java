/**
 *  :: Rest Server ::
 * ====================
 */
package com.rpsoft.rest.ws.simulator.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.rpsoft.rest.ws.simulator.server.impl.Calculator;

/**
 * In order to deploy our WebService we need a ServletContainer for which we
 * will use Jersey and a WebServer into which we can drop the container for
 * which we will use Jetty.
 * 
 * @author vivek.ksingh
 */
public class RestServerApp {

	/**
	 * Start server application.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {

		final String contextPath = "/";
		final String containerPath = "/*";
		final String resourceImplKey = "jersey.config.server.provider.classnames";
		final int serverPort = 3019;
		final int initOrder = 0;
		
		
		
		final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath(contextPath);

		final Server jettyServer = new Server(serverPort);
		jettyServer.setHandler(context);

		final ServletHolder jerseyServletHolder = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, containerPath);
		jerseyServletHolder.setInitOrder(initOrder);
		jerseyServletHolder.setInitParameter(resourceImplKey, Calculator.class.getCanonicalName());

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