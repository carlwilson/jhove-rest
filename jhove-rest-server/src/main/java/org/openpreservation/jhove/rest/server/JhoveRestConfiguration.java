package org.openpreservation.jhove.rest.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.dropwizard.Configuration;

/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>
 *
 * @version 0.1
 */

public class JhoveRestConfiguration extends Configuration {

	private int port;

	@JsonProperty("swagger")
	public SwaggerBundleConfiguration swaggerBundleConfiguration;

	/**
	 * Default no-arg constructor
	 */
	public JhoveRestConfiguration() {
		super();
	}

	/**
	 * @return the TCP/IP port used
	 */
	@JsonProperty
	public int getPort() {
		return this.port;
	}

	/**
	 * @param port
	 *            numeric value of TCP/IP port to listen to
	 */
	@JsonProperty
	public void setPort(int port) {
		this.port = port;
	}

}
