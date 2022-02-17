/**
 * 
 */
package org.openpreservation.jhove.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import edu.harvard.hul.ois.jhove.Checksum;
import edu.harvard.hul.ois.jhove.ProfileResults;
import edu.harvard.hul.ois.jhove.PropertyType;
import edu.harvard.hul.ois.jhove.RepInfo;

/**
 * @author cfw
 *
 */
@XmlRootElement
public final class ConformanceReport {
	public final String fileName;
	public final long size;
	public final List<Checksum> checksums;
	public final Date created;
	public final Date lastMod;
	public final List<String> sigMatches;
	public final String mimeType;
	public final String format;
	public final int wellFormed;
	public final String message;
	public final Set<ProfileResults> profiles;
	public final List<Property> properties;
	public final List<PolicyCheck> policyChecks;

	public ConformanceReport(RepInfo repInfo, final List<PolicyCheck> policyChecks) {
		this.fileName = repInfo.getUri();
		this.checksums = repInfo.getChecksum();
		this.created = repInfo.getCreated();
		this.lastMod = repInfo.getLastModified();
		this.mimeType = repInfo.getMimeType();
		this.format = repInfo.getFormat();
		this.sigMatches = Collections.unmodifiableList(repInfo.getSigMatch());
		this.profiles = Collections.unmodifiableSet(repInfo.getProfileResults());
		this.size = repInfo.getSize();
		this.wellFormed = repInfo.getWellFormed();
		this.message = message(this.wellFormed > 0);
		this.properties = Collections.unmodifiableList(propList(repInfo));
		this.policyChecks = Collections.unmodifiableList(policyChecks);
	}

	static private String message(final boolean isWellFormed) {
		return ((isWellFormed) ? "Well-formed." : "Not well-Formed.");
	}
	
	static private final List<Property> propList(RepInfo repInfo) {
		List<Property> retVal = new ArrayList<>();
		for (edu.harvard.hul.ois.jhove.Property prop : repInfo.getProperty().values()) {
			retVal.add(new Property(prop.getName(), (prop.getType() == PropertyType.INTEGER) ? Property.INT : Property.STRING, prop.getValue().toString()));
		}
		return retVal;
	}
}
