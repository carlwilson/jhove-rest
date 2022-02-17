/**
 * 
 */
package org.openpreservation.jhove.modules;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author cfw
 *
 */
@XmlRootElement
public class Property {
	public final static String STRING = "string";
	public final static String INT = "int";
	public final static Property DEFAULT_INT = new Property("int", INT, "0");
	public final static Property DEFAULT_STRING = new Property("string", STRING, "");
	public final String name;
	public final String type;
	public final String value;
	
	public Property(final String name, final String type, final String value) throws NumberFormatException {
		this.name = name;
		this.type = type;
		this.value = value;
		if (this.isInt()) Integer.parseInt(value); 
	}
	
	public boolean equals(final Property prop) {
		if (this.type != prop.type) return false;
		if (this.isInt()) return this.intCompare(prop) == 0;
		return this.value.compareTo(prop.value) == 0;
	}

	public boolean gt(final Property prop) {
		if (this.type != prop.type) return false;
		if (this.isInt()) return this.intCompare(prop) > 0;
		return this.value.compareTo(prop.value) > 0;
	}

	public boolean lt(final Property prop) {
		if (this.type != prop.type) return false;
		if (this.isInt()) return this.intCompare(prop) < 0;
		return this.value.compareTo(prop.value) < 0;
	}
	
	private int intCompare(final Property prop) {
		Integer val = Integer.parseInt(this.value);
		Integer toComp = Integer.parseInt(prop.value);
		return val.compareTo(toComp);
	}
	
	private boolean isInt() {
		return INT.equals(this.type);
	}
}
