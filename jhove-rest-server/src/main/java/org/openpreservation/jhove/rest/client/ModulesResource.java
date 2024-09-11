/**
 * 
 */
package org.openpreservation.jhove.rest.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openpreservation.jhove.rest.client.views.ModulesView;

/**
 * @author cfw
 *
 */
@Path("/modules")
@Produces({ MediaType.TEXT_HTML })
public final class ModulesResource {

	/**
	 * 
	 */
	public ModulesResource() {
		super();
	}

	@GET
	public ModulesView getModules() {
		return new ModulesView();
	}
}
