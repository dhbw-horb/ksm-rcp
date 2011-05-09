package de.dhbw.horb.ksm.core.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import de.dhbw.horb.ksm.core.editor.figures.KSMNodeGroupFigure;
import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.model.property.ModelPropertySource;
import de.dhbw.horb.ksm.core.editor.parts.editmanager.NodeGroupDirectEditManager;
import de.dhbw.horb.ksm.core.editor.policies.DiagramLayoutEditPolicy;
import de.dhbw.horb.ksm.core.editor.policies.NodeGroupComponentEditPolicy;
import de.dhbw.horb.ksm.core.editor.policies.NodeGroupDirectEditPolicy;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.api.Properties;

/**
 * {@link KSMNodeGroupEditPart} is the Editor Part of {@link NodeGroup} class.
 */
@SuppressWarnings({ "rawtypes" })
public class KSMNodeGroupEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener, NodeEditPart {

	class NodeConnectionAnchor extends ChopboxAnchor {
		public NodeConnectionAnchor(IFigure figure) {
			super(figure);
		}
	}

	private NodeGroupDirectEditManager manager;

	private final static String LOCATION_X = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_LOCATION_X);
	private final static String LOCATION_Y = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_LOCATION_Y);
	private final static String HEIGHT = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_HEIGHT);
	private final static String WIDTH = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_WIDTH);
	private final static String CAPTION = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_CAPTION);

	/**
	 * register with the model for property changes.
	 */
	@Override
	public void activate() {
		if (isActive()) {
			return;
		}
		super.activate();
		getModel().addPropertyChangeListener(this);
		getModel().getProperties().addPropertyChangeListener(this);
	}

	@Override
	public Object getAdapter(Class key) {
		if (key.equals(IPropertySource.class)) {
			// Property Description for this Model-Object
			return new ModelPropertySource.NodeGroupPropertySource(getModel());
		}
		return super.getAdapter(key);
	}

	// Override from AbstractEditPart
	@Override
	protected void createEditPolicies() {
		// Delete
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new NodeGroupComponentEditPolicy());

		// DirectEditPolicy applies DirectEditRequests (see performRequest())
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new NodeGroupDirectEditPolicy(this));

		// install a custom layout policy that handles dragging nodes around
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new DiagramLayoutEditPolicy());
	}

	// Override from AbstractGraphicalEditPart
	@Override
	protected IFigure createFigure() {
		return new KSMNodeGroupFigure();
	}

	/**
	 * deregister with the model
	 */
	@Override
	public void deactivate() {
		if (!isActive()) {
			return;
		}
		super.deactivate();
		getModel().removePropertyChangeListener(this);
		getModel().getProperties().removePropertyChangeListener(this);
	}

	@Override
	public KSMNodeGroupFigure getFigure() {
		return (KSMNodeGroupFigure) super.getFigure();
	}

	@Override
	public NodeGroup getModel() {
		return (NodeGroup) super.getModel();
	}

	@Override
	protected List getModelChildren() {
		List<Object> childs = new ArrayList<Object>();
		childs.addAll(getModel().getNodes());
		childs.addAll(getModel().getNodeGroups());
		return childs;
	}

	// override from NodeEditPart
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new NodeConnectionAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new NodeConnectionAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new NodeConnectionAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new NodeConnectionAnchor(getFigure());
	}

	@Override
	public void performRequest(Request request) {
		if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
			if (manager == null) {
				manager = new NodeGroupDirectEditManager(this);
			}
			manager.show();
		}
	}

	// XXX
	@Override
	public void propertyChange(PropertyChangeEvent changeEvent) {
		String pName = changeEvent.getPropertyName();
		boolean handled = false;

		if (changeEvent.getSource() == getModel()) {
			if ("nodes".equals(pName) || "nodegroups".equals(pName)) {
				handled = true;
				// Es wurden in diese NodeGroup Unterelemente eingefügt
				refreshChildren();
			}
		} else if (changeEvent.getSource() == getModel().getProperties()) {
			if (ModelProperties.INSTANCE.NODEGROUP_VISUAL_LOCATION_X
					.equals(pName)) {
				handled = true;
				refresh();
			} else if (ModelProperties.INSTANCE.NODEGROUP_VISUAL_LOCATION_Y
					.equals(pName)) {
				handled = true;
				refresh();
			} else if (ModelProperties.INSTANCE.NODEGROUP_VISUAL_WIDTH
					.equals(pName)) {
				handled = true;
				refresh();
			} else if (ModelProperties.INSTANCE.NODEGROUP_VISUAL_HEIGHT
					.equals(pName)) {
				handled = true;
				refresh();
			} else if (ModelProperties.INSTANCE.NODEGROUP_VISUAL_CAPTION
					.equals(pName)) {
				handled = true;
				refreshVisuals();
			}
		}

		if (!handled) {
			System.out.println("Unhandled Datemodell event: " + changeEvent);
		}
	}

	@Override
	protected void refreshVisuals() {
		NodeGroup model2 = getModel();
		Properties properties = model2.getProperties();
		BigInteger posX = properties.getInteger(LOCATION_X);
		if (posX == null) {
			posX = BigInteger.valueOf(10);
			properties.setInteger(LOCATION_X, posX);
		}
		BigInteger posY = properties.getInteger(LOCATION_Y);
		if (posY == null) {
			posY = BigInteger.valueOf(10);
			properties.setInteger(LOCATION_Y, posY);
		}

		BigInteger width = properties.getInteger(WIDTH);
		if (width == null) {
			width = BigInteger.valueOf(100);
			properties.setInteger(WIDTH, width);
		}
		BigInteger height = properties.getInteger(HEIGHT);
		if (height == null) {
			height = BigInteger.valueOf(100);
			properties.setInteger(HEIGHT, height);
		}

		Point loc = new Point(posX.intValue(), posY.intValue());

		Dimension size = new Dimension(width.intValue(), height.intValue());
		Rectangle rectangle = new Rectangle(loc, size);

		String caption = properties.getString(CAPTION);
		if (caption == null) {
			caption = "NodeGroup";
			properties.setString(CAPTION, caption);
		}
		getFigure().setCaption(caption);

		// tells the parent part (in this case DiagramPart) that this part
		// and its figure are to be constrained to the given rectangle
		// coordinates are relative
		GraphicalEditPart parent = (GraphicalEditPart) getParent();
		parent.setLayoutConstraint(this, getFigure(), rectangle);
	}
}