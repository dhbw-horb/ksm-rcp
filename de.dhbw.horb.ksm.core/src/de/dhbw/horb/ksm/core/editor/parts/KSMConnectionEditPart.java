package de.dhbw.horb.ksm.core.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.ui.views.properties.IPropertySource;

import de.dhbw.horb.ksm.core.editor.commands.ConnectionDeleteCommand;
import de.dhbw.horb.ksm.core.editor.model.property.ModelPropertySource;
import de.dhbw.horb.ksm.core.editor.parts.routing.ModifiedAutomaticRouter;
import de.dhbw.horb.ksm.core.editor.parts.routing.RoundedPolylineConnection;
import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.Node;

/**
 * Adapts the Model Object {@link de.dhbw.horb.ksm.xmlschema.api.Connection} to
 * the Editor GUI.
 */
public class KSMConnectionEditPart extends AbstractConnectionEditPart implements
		PropertyChangeListener {
	private final static FanRouter FAN_ROUTER;
	@SuppressWarnings("unused")
	private final static SelfReferencingConnectionRouter SELF_CONNECTION_ROUTER;

	static {
		FAN_ROUTER = new FanRouter();
		FAN_ROUTER.setSeparation(32);
		SELF_CONNECTION_ROUTER = new SelfReferencingConnectionRouter();
	}

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
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (adapter.equals(IPropertySource.class)) {
			// Property Description for this Model-Object
			return new ModelPropertySource.ConnectionPropertySource(getModel());
		}
		return super.getAdapter(adapter);
	}

	@Override
	public Connection getModel() {
		return (Connection) super.getModel();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectionEndpointEditPolicy() {
					protected void addSelectionHandles() {
						super.addSelectionHandles();
						getConnectionFigure().setLineWidth(2);
					}

					protected Shape getConnectionFigure() {
						return (Shape) ((GraphicalEditPart) getHost())
								.getFigure();
					}

					protected void removeSelectionHandles() {
						super.removeSelectionHandles();
						getConnectionFigure().setLineWidth(0);
					}
				});

		// Provides Command to delete the Connection hold by this EditPart
		installEditPolicy(EditPolicy.CONNECTION_ROLE,
				new ConnectionEditPolicy() {
					@Override
					protected Command getDeleteCommand(GroupRequest arg0) {
						ConnectionDeleteCommand command = new ConnectionDeleteCommand();
						command.setConnection(KSMConnectionEditPart.this
								.getModel());
						command.setSourceNode((Node) getSource().getModel());
						return command;
					}
				});
	}

	/**
	 * Returns a newly created Figure to represent the connection.
	 *
	 * @return The created Figure.
	 */
	@Override
	protected IFigure createFigure() {
		RoundedPolylineConnection roundedConnection = new RoundedPolylineConnection();
		setFigureProperties(roundedConnection);
		return roundedConnection;
	}

	private void setFigureProperties(org.eclipse.draw2d.Connection connection) {
		RoundedPolylineConnection roundedConnection = (RoundedPolylineConnection) connection;
		// if (((Node)
		// getParent().getModel()).getId().equals(getModel().getTo())) {
		// // Connection auf sich selbst
		// roundedConnection.setTolerance(1);
		// roundedConnection.setCornerLength(32);
		// roundedConnection.setCurved(false);
		// roundedConnection.setTargetDecoration(new PolygonDecoration());
		// roundedConnection.setConnectionRouter(SELF_CONNECTION_ROUTER);
		// } else {
		// Connection auf einen anderen Knoten
		roundedConnection.setTolerance(5);
		roundedConnection.setCornerLength(32);
		roundedConnection.setCurved(true);
		roundedConnection.setTargetDecoration(new PolygonDecoration());
		roundedConnection.setConnectionRouter(FAN_ROUTER);
		// }
	}

	@Override
	public void refresh() {
		// The connection maybe has been re-connected
		// so the Connection Router must
		// be changed
		setFigureProperties(getConnectionFigure());
		super.refresh();
	}

	/**
	 * Inserts a "middle" Point between start and end point. with
	 * y=start_point.x + offset*index where index is the number of this
	 * connection in a list of all self-referencing connections of this figure
	 */
	public static class SelfReferencingConnectionRouter extends
			ModifiedAutomaticRouter {
		@Override
		protected void handleCollision(
				org.eclipse.draw2d.Connection connection, PointList list,
				int index) {
			PointList points = connection.getPoints();
			points.removeAllPoints();
			Point startPoint, midpoint, endPoint;

			startPoint = getStartPoint(connection);
			startPoint.x -= 80;

			endPoint = getEndPoint(connection);
			endPoint.x += 40;

			midpoint = new Point(startPoint);
			midpoint.y += 20 + 20 * index;
			midpoint.x = (startPoint.x + endPoint.x) / 2;

			connection.translateToRelative(startPoint);
			points.addPoint(startPoint);

			connection.translateToRelative(midpoint);
			points.addPoint(midpoint);

			connection.translateToRelative(endPoint);
			points.addPoint(endPoint);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		final String pName = evt.getPropertyName();
		boolean handled = false;

		if (!handled) {
			System.out.println("Unhandled KSM event: " + pName + " - " + evt);
		}
	}
}