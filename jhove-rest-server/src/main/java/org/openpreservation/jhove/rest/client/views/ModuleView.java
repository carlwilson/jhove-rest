/**
 * 
 */
package org.openpreservation.jhove.rest.client.views;

import org.openpreservation.jhove.modules.ModuleDetails;

import io.dropwizard.views.View;

/**
 * @author cfw
 *
 */
public final class ModuleView extends View {
	private final ModuleDetails module;
	public ModuleView(ModuleDetails module) {
		super("module.mustache");
		this.module = module;
	}
	
	public ModuleDetails getModule() {
		return this.module;
	}
}
