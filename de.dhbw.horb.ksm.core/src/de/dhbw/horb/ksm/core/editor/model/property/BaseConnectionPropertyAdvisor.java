package de.dhbw.horb.ksm.core.editor.model.property;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.extension.AbstractPropertyDescriptorAdvisor;

/**
 * Add Basic NodeProperties.
 */
public class BaseConnectionPropertyAdvisor extends AbstractPropertyDescriptorAdvisor {

	@Override
	protected void init() {
		addProperty(ModelProperties.INSTANCE.CONNECTION_VISUAL_CAPTION, "Caption");
	}
}
