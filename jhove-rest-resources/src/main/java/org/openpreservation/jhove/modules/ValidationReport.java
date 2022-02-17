/**
 * 
 */
package org.openpreservation.jhove.modules;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.harvard.hul.ois.jhove.Checksum;
import edu.harvard.hul.ois.jhove.Message;
import edu.harvard.hul.ois.jhove.RepInfo;

/**
 * @author cfw
 *
 */
@XmlRootElement
public final class ValidationReport {
	public final String fileName;
	public final List<Checksum> checksums;
	public final Date created;
	public final Date lastMod;
	public final String mimeType;
	public final String format;
	public final List<Message> messages;
	public final List<String> sigMatches;
	public final long size;
	public final int valid;
	public final int wellFormed;
	public final String message;

	public ValidationReport(RepInfo repInfo) {
		this.fileName = repInfo.getUri();
		this.checksums = repInfo.getChecksum();
		this.created = repInfo.getCreated();
		this.lastMod = repInfo.getLastModified();
		this.mimeType = repInfo.getMimeType();
		this.format = repInfo.getFormat();
		this.messages = Collections.unmodifiableList(repInfo.getMessage());
		this.sigMatches = Collections.unmodifiableList(repInfo.getSigMatch());
		this.size = repInfo.getSize();
		this.valid = repInfo.getValid();
		this.wellFormed = repInfo.getWellFormed();
		this.message = message(this.wellFormed > 0, this.valid > 0);
	}

	static private String message(final boolean isWellFormed, final boolean isValid) {
		final String wellFormed = ((isWellFormed) ? "Well-formed " : "Not well-Formed.");
		if (!isWellFormed) {
			return wellFormed;
		}
		return wellFormed + ((isValid) ? "and valid." : "but not Valid.");	}
}
