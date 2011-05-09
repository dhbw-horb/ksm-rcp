package de.dhbw.horb.ksm.core.editor.parts.outline;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import de.dhbw.horb.ksm.core.Activator;
import de.dhbw.horb.ksm.core.editor.model.property.ModelPropertySource;
import de.dhbw.horb.ksm.core.editor.policies.NodeGroupComponentEditPolicy;
import de.dhbw.horb.ksm.model.api.NodeGroup;

/**
 * represent a {@link NodeGroup} in a TreeViewer (e.g. OutlinePage)
 */
public class TreeNodeGroupEditPart extends AbstractTreeEditPart implements
		PropertyChangeListener {

	public TreeNodeGroupEditPart(NodeGroup model) {
		super(model);
	}

	@Override
	public void activate() {
		super.activate();
		getModel().addPropertyChangeListener(this);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class key) {
		if (key.equals(IPropertySource.class)) {
			// Property Description for this Model-Object
			return new ModelPropertySource.NodeGroupPropertySource(getModel());
		}
		return super.getAdapter(key);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new NodeGroupComponentEditPolicy());
	}

	@Override
	public void deactivate() {
		getModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	public NodeGroup getModel() {
		return (NodeGroup) super.getModel();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		List<Object> childs = new ArrayList<Object>();
		childs.addAll(getModel().getNodeGroups());
		childs.addAll(getModel().getNodes());
		return childs;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refreshChildren();
		refreshVisuals();
	}

	@Override
	protected void refreshVisuals() {
		setWidgetText("NodeGroup: " + getModel().getId());
		setWidgetImage(Activator.getImageDescriptor(
				"icons/16/ksm-nodegroup.png").createImage());
	}
}