package org.openpreservation.jhove.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openpreservation.rest.environment.Environment;
import org.openpreservation.rest.environment.Environments;

import io.swagger.annotations.Api;
import io.swagger.annotations.License;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
	
/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>
 *
 * @version 0.1
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "jhove")
@SwaggerDefinition(info=@Info(
        description = "Rest API for Jhove",
        version = "V0.0.2",
        title = "Jhove API",
        license = @License(name = "Apache 2.0", url = "http://www.apache.org")

),
        schemes = {SwaggerDefinition.Scheme.HTTP}
)

public final class ApiResource {

	/**
	 * 
	 */
	public ApiResource() {
		super();
	}

	@GET
	@Path("/info")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public static Environment getEnvironent() {
		return Environments.getEnvironment();
	}

	/**
	 * @return a new {@link ByteStreamResource}
	 */
	@Path("/bytes/")
	public static ByteStreamResource byteResources() {
		return new ByteStreamResource();
	}

	/**
	 * @return a new {@link ByteStreamResource}
	 */
	@Path("/jhove/")
	public static JhoveResources jhoveResources() {
		return new JhoveResources();
	}
}
