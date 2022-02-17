/**
 * 
 */
package org.openpreservation.jhove.modules;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import edu.harvard.hul.ois.jhove.Module;

/**
 * @author cfw
 *
 */
@XmlRootElement
public class ModuleId {

	public final String name;
	public final Date date;
	public final String release;

	private ModuleId(final String name, final Date date, final String release) {
		super();
		this.name = name;
		this.date = date;
		this.release = release;
	}

	public static final ModuleId fromModule(final Module module) {
		return new ModuleId(module.getName(), module.getDate(), module.getRelease());
	}
}
