package org.openpreservation.jhove.modules;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class PolicyRule {
	private final static String[] OPS = {"=", "<", ">"};
	public final static List<String> OPERATORS = Arrays.asList(OPS);
	public final String operator;
	public final Property prop;
	
	public PolicyRule(final String operator, final String type, final String value) {
		if (!OPERATORS.contains(operator)) {
			throw new IllegalArgumentException("Illegal Operator");
		}
		this.operator = operator;
		this.prop = new Property("comp", type, value);
	}
	
	public boolean apply(final Property prop) {
		if (this.operator.equals("=")) {
			return prop.equals(this.prop);
		}
		if (this.operator.equals(">")) {
			return prop.gt(this.prop);
		}
		return prop.lt(this.prop);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getName());
		sb.append(" [operator='");
		sb.append(this.operator);
		sb.append("', prop='");
		sb.append(this.prop.name);
		sb.append("']");
		return sb.toString();
	}
}
