package de.dhbw.horb.ksm.core.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import de.dhbw.horb.ksm.core.editor.model.property.ModelPropertySource;
import de.dhbw.horb.ksm.core.editor.policies.DiagramLayoutEditPolicy;
import de.dhbw.horb.ksm.core.editor.policies.NodeGroupComponentEditPolicy;
import de.dhbw.horb.ksm.model.api.KSM;

/**
 * The Edit part adapts the root NodeGroup from the {@link KSM} model Object to
 * the Editor-GUI.
 */
public class KSMEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener {

	@Override
	public void activate() {
		if (isActive()) {
			return;
		}
		super.activate();
		getModel().getProperties().addPropertyChangeListener(this);
		getModel().getNodeGroup().addPropertyChangeListener(this);
	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class key) {
		if (key.equals(IPropertySource.class)) {
			// Property Description for this Model-Object
			return new ModelPropertySource.KSMPropertySource(getModel());
		}
		return super.getAdapter(key);
	}

	protected void createEditPolicies() {
		// Delete
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new NodeGroupComponentEditPolicy());

		// install a custom layout policy that handles dragging nodes around
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramLayoutEditPolicy());
	}

	protected IFigure createFigure() {
		IFigure pane = new Figure() {
			@Override
			protected boolean useLocalCoordinates() {
				return false;
			}
		};
		pane.setLayoutManager(new XYLayout());
		return pane;
	}

	@Override
	public void deactivate() {
		if (!isActive()) {
			return;
		}
		super.deactivate();
		getModel().getProperties().removePropertyChangeListener(this);
		getModel().getNodeGroup().removePropertyChangeListener(this);
	}

	@Override
	public KSM getModel() {
		return (KSM) super.getModel();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List getModelChildren() {
		List list = new ArrayList();
		list.addAll(getModel().getNodeGroup().getNodes());
		list.addAll(getModel().getNodeGroup().getNodeGroups());
		return list;
	}

	public void propertyChange(PropertyChangeEvent event) {
		boolean handled = false;
		String pName = event.getPropertyName();
		if (event.getSource() == getModel().getProperties()) {

		} else if (event.getSource() == getModel().getNodeGroup()) {
			if ("nodes".equals(pName) || "nodegroups".equals(pName)) {
				handled = true;
				refreshChildren();
			}
		}
		if (!handled) {
			System.out.println("Unhandled Datemodell event: " + event);
		}
	}

	@Override
	protected void refreshVisuals() {
		getFigure().revalidate();
	}
}