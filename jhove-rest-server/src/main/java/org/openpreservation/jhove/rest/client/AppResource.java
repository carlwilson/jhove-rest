/**
 * 
 */
package org.openpreservation.jhove.rest.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openpreservation.jhove.rest.client.views.AppView;

/**
 * @author cfw
 *
 */
@Path("/")
@Produces({ MediaType.TEXT_HTML })
public final class AppResource {

	/**
	 * 
	 */
	public AppResource() {
		super();
	}

	@GET
	public AppView getApp() {
		System.err.println("APP");
		return new AppView();
	}
}
