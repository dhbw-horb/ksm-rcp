package de.dhbw.horb.ksm.qksm;

import de.dhbw.horb.ksm.core.extension.AbstractPropertyDescriptorAdvisor;

public class QKSMNodePropertyDescriptorAdvisor extends
		AbstractPropertyDescriptorAdvisor {

	@Override
	protected void init() {
		addProperty("string:foobar", "foobar");
	}
}
