/**
 * 
 */
package org.openpreservation.jhove.modules;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.harvard.hul.ois.jhove.Module;
import edu.harvard.hul.ois.jhove.Signature;

/**
 * @author cfw
 *
 */
@XmlRootElement
public final class FormatDetails {

	public final String[] formats;
	public final String[] mimeTypes;
	public final List<Signature> signatures;

	private FormatDetails(final String[] formats, final String[] mimeTypes, final List<Signature> signatures) {
		super();
		this.formats = formats;
		this.mimeTypes = mimeTypes;
		this.signatures = Collections.unmodifiableList(signatures);
	}

	public static final FormatDetails fromModule(final Module module) {
		return new FormatDetails(module.getFormat(), module.getMimeType(), module.getSignature());
	}
}
