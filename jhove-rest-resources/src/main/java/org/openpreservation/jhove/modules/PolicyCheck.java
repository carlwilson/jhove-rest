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
public class PolicyCheck {
	public final Property checked;
	public final PolicyRule rule;
	public final boolean passed;
	
	public PolicyCheck(final Property toCheck, final PolicyRule rule) {
		this.checked = toCheck;
		this.rule = rule;
		this.passed = rule.apply(toCheck);
	}
}
