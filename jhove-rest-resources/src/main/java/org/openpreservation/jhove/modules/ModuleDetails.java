/**
 * 
 */
package org.openpreservation.jhove.modules;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.harvard.hul.ois.jhove.Agent;
import edu.harvard.hul.ois.jhove.Module;

/**
 * @author cfw
 *
 */
@XmlRootElement
public final class ModuleDetails {

	public final ModuleId moduleId;
	public final FormatDetails formatDetails;
	public final Agent agent;
	public final String coverage;
	public final String rights;
	public final List<String> features;

	private ModuleDetails(final ModuleId moduleId, final FormatDetails formatDetails, final Module module) {
		this.moduleId = moduleId;
		this.formatDetails = formatDetails;
		this.agent = module.getVendor();
		this.coverage = module.getCoverage();
		this.rights = module.getRights();
		this.features = module.getFeatures();
	}

	public static final ModuleDetails fromModule(final Module module) {
		return new ModuleDetails(ModuleId.fromModule(module), FormatDetails.fromModule(module), module);
	}
}
