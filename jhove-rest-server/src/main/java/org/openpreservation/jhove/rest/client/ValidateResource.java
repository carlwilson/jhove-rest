/**
 * 
 */
package org.openpreservation.jhove.rest.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openpreservation.jhove.rest.client.views.ValidateView;

/**
 * @author cfw
 *
 */
@Path("/validate")
@Produces({ MediaType.TEXT_HTML })
public final class ValidateResource {

	/**
	 * 
	 */
	public ValidateResource() {
		super();
	}

	@GET
	public static ValidateView validate() {
		return new ValidateView();
	}
}
