package de.dhbw.horb.ksm.core.editor.parts.outline;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import de.dhbw.horb.ksm.core.Activator;
import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.model.property.ModelPropertySource;
import de.dhbw.horb.ksm.core.editor.policies.NodeComponentEditPolicy;
import de.dhbw.horb.ksm.model.api.Node;

/**
 * represent a {@link Node} in a TreeViewer (e.g. OutlinePage)
 */
public class TreeNodeEditPart extends AbstractTreeEditPart implements
		PropertyChangeListener {

	public TreeNodeEditPart(Node model) {
		super(model);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class key) {
		if (key.equals(IPropertySource.class)) {
			// Property Description for this Model-Object
			return new ModelPropertySource.NodePropertySource(getModel());
		}
		return super.getAdapter(key);
	}

	@Override
	public Node getModel() {
		return (Node) super.getModel();
	}

	@Override
	public void activate() {
		super.activate();
		getModel().addPropertyChangeListener(this);
		getModel().getProperties().addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		getModel().getProperties().removePropertyChangeListener(this);
		getModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	public void propertyChange(PropertyChangeEvent change) {
		refreshVisuals();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new NodeComponentEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		final String caption = getModel()
				.getProperties()
				.getString(
						ModelProperties.INSTANCE
								.stripType(ModelProperties.INSTANCE.NODE_VISUAL_CAPTION));
		if (caption != null) {
			setWidgetText("Node: " + caption);
		} else {
			setWidgetText("Node: " + getModel().getId());
		}

		setWidgetImage(Activator.getImageDescriptor("icons/16/ksm-node.png")
				.createImage());
	}
}