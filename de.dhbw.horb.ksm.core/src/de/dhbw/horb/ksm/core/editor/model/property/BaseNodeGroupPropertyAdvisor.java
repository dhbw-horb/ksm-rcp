package de.dhbw.horb.ksm.core.editor.model.property;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.extension.AbstractPropertyDescriptorAdvisor;

/**
 * Add Basic NodeProperties.
 */
public class BaseNodeGroupPropertyAdvisor extends AbstractPropertyDescriptorAdvisor {

	@Override
	protected void init() {
		addProperty(ModelProperties.INSTANCE.NODEGROUP_VISUAL_CAPTION, "Caption");
	}
}
