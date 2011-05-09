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

import de.dhbw.horb.ksm.core.editor.figures.KSMNodeFigure;
import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.model.property.ModelPropertySource;
import de.dhbw.horb.ksm.core.editor.parts.editmanager.NodeDirectEditManager;
import de.dhbw.horb.ksm.core.editor.policies.NodeComponentEditPolicy;
import de.dhbw.horb.ksm.core.editor.policies.NodeDirectEditPolicy;
import de.dhbw.horb.ksm.core.editor.policies.NodeGraphicalNodeEditPolicy;
import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.Properties;

@SuppressWarnings({ "rawtypes" })
public class KSMNodeEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener, NodeEditPart {

	class NodeConnectionAnchor extends ChopboxAnchor {
		public NodeConnectionAnchor(IFigure figure) {
			super(figure);
		}
	}

	private NodeDirectEditManager manager;

	private final static String LOCATION_X = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_X);
	private final static String LOCATION_Y = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_Y);
	private final static String CAPTION = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODE_VISUAL_CAPTION);
	private final static String COLOR = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODE_VISUAL_COLOR);

	/**
	 * register with the model for property changes
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
	protected void createEditPolicies() {
		// Delete
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new NodeComponentEditPolicy());

		// Connect, Reconnect, disconnect
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new NodeGraphicalNodeEditPolicy());

		// DirectEditPolicy applies DirectEditRequests (see performRequest())
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new NodeDirectEditPolicy());
	}

	@Override
	protected IFigure createFigure() {
		return new KSMNodeFigure();
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
	public Object getAdapter(Class key) {
		if (key.equals(IPropertySource.class)) {
			// Property Description for this Model-Object
			return new ModelPropertySource.NodePropertySource(getModel());
		}
		return super.getAdapter(key);
	}

	@Override
	public KSMNodeFigure getFigure() {
		return (KSMNodeFigure) super.getFigure();
	}

	@Override
	public Node getModel() {
		return (Node) super.getModel();
	}

	@Override
	protected List getModelSourceConnections() {
		return getModel().getConnections();
	}

	@Override
	protected List getModelTargetConnections() {
		List<Object> list = new ArrayList<Object>();
		KSM model2 = (KSM) getRoot().getContents().getModel();

		// FIXME design flaw
		for (Node n : model2.getAllNodes()) {
			for (Connection c : n.getConnections()) {
				if (c.getTo() != null && c.getTo().equals(getModel().getId())) {
					list.add(c);
				}
			}
		}
		return list;
	}

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
				manager = new NodeDirectEditManager(this);
			}
			manager.show();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent changeEvent) {
		boolean handled = false;
		final String pName = changeEvent.getPropertyName();
		if (changeEvent.getSource() == getModel()) {
			if ("connections".equals(pName)) {
				handled = true;
				// Refresh source
				refreshSourceConnections();

				// refresh target, find target model
				Connection connection;
				if (changeEvent.getNewValue() != null)
					connection = (Connection) changeEvent.getNewValue();
				else
					connection = (Connection) changeEvent.getOldValue();

				KSM ksm = (KSM) getRoot().getContents().getModel();
				Node lookupNode = ksm.lookupNode(connection.getTo());

				// find target editpart
				List<KSMNodeEditPart> editParts = new ArrayList<KSMNodeEditPart>();
				for (Object key : getViewer().getEditPartRegistry().keySet()) {
					if (key.equals(lookupNode)) {
						KSMNodeEditPart editPart = (KSMNodeEditPart) getViewer()
								.getEditPartRegistry().get(key);
						editParts.add(editPart);
					}
				}
				for (KSMNodeEditPart ksmNodeEditPart : editParts) {
					ksmNodeEditPart.refreshTargetConnections();
				}
			}
		}
		if (changeEvent.getSource() == getModel().getProperties()) {
			if (ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_X.equals(pName)) {
				handled = true;
				refresh();
			} else if (ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_Y
					.equals(pName)) {
				handled = true;
				refresh();
			} else if (ModelProperties.INSTANCE.NODE_VISUAL_CAPTION
					.equals(pName)) {
				handled = true;
				refreshVisuals();
			} else if (ModelProperties.INSTANCE.NODE_VISUAL_COLOR.equals(pName)) {
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
		Properties properties = getModel().getProperties();

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

		GraphicalEditPart parent = (GraphicalEditPart) getParent();

		Point loc = new Point(posX.intValue(), posY.intValue());
		Dimension size = new Dimension(150, 40);
		Rectangle rectangle = new Rectangle(loc, size);

		String caption = properties.getString(CAPTION);
		if (caption == null) {
			caption = "ein Titel";
			properties.setString(CAPTION, caption);
		}
		getFigure().setCaption(caption);

		String color = properties.getString(COLOR);
		if (color != null) {
			getFigure().setNodeColor(
					ModelProperties.INSTANCE.stringToRGB(color));
		}

		// Tell parent NodeGroup that this Node should be constraint by
		// _rectangle_
		getFigure().translateToParent(rectangle);
		parent.setLayoutConstraint(this, getFigure(), rectangle);

		getFigure().repaint();
	}
}