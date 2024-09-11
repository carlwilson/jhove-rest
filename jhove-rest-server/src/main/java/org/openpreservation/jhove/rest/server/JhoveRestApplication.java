package org.openpreservation.jhove.rest.server;

import org.glassfish.jersey.server.validation.internal.ValidationExceptionMapper;
import org.openpreservation.jhove.rest.exceptions.HttpJhoveExceptionMapper;
import org.openpreservation.jhove.rest.exceptions.IllegalStateExceptionMapper;
import org.openpreservation.jhove.rest.resources.ApiResource;

import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.xml.XmlBundle;

/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>
 *
 * @version 0.1
 *
 *          Created 2 Aug 2018:16:20:27
 */

public class JhoveRestApplication extends Application<JhoveRestConfiguration> {
	private static final String NAME = "jhove-rest"; //$NON-NLS-1$

	/**
	 * Main method for Jetty server application. Simply calls the run method with
	 * command line args.
	 *
	 * @param args command line arguments as string array.
	 * @throws Exception passes any exception thrown by run
	 */
	public static void main(String[] args) throws Exception {
		new JhoveRestApplication().run(args);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void initialize(Bootstrap<JhoveRestConfiguration> bootstrap) {
		bootstrap.addBundle(new MultiPartBundle());
		// Dropwizard assets bundle to map static resources
		bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html", "static")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		bootstrap.addBundle(new ViewBundle<JhoveRestConfiguration>());
		bootstrap.addBundle(new ViewBundle<JhoveRestConfiguration>());
		bootstrap.addBundle(new SwaggerBundle<JhoveRestConfiguration>() {@Override
		protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(JhoveRestConfiguration configuration) {
			return configuration.swaggerBundleConfiguration;
		}});
		bootstrap.addBundle(new XmlBundle());
	}

	@Override
	public void run(JhoveRestConfiguration configuration, Environment environment) {
		// Create & register our REST resources
		final ApiResource restApi = new ApiResource();
		environment.jersey().register(restApi);
		// Setup exception mapping to integrate JHOVE exceptions with appropriate HTTP
		// codes
		final ValidationExceptionMapper vem = new ValidationExceptionMapper();
		environment.jersey().register(vem);
		environment.jersey().register(new HttpJhoveExceptionMapper());
		environment.jersey().register(new IllegalStateExceptionMapper());
		// Set up cross domain REST
		environment.jersey().register(CORSResponseFilter.class);
	}
}
