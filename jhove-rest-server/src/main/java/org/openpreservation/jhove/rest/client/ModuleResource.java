/**
 * 
 */
package org.openpreservation.jhove.rest.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openpreservation.jhove.rest.client.views.ModuleView;
import org.openpreservation.jhove.rest.resources.JhoveResources;

/**
 * @author cfw
 *
 */
@Path("/modules/{name}")
@Produces({ MediaType.TEXT_HTML })
public final class ModuleResource {

	/**
	 * 
	 */
	public ModuleResource() {
		super();
	}

	@GET
	public ModuleView getModule(@PathParam("name") String name) {
		
		return new ModuleView(JhoveResources.module(name));
	}
}
