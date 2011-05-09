package de.dhbw.horb.ksm.core.editor.commands;

import java.math.BigInteger;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.model.NodeRequest;
import de.dhbw.horb.ksm.model.api.Node;

public class NodeCreateCommand extends Command {
	private NodeRequest nodeRequest;
	private Node node = null;
	private Point absoluteLocation;

	private final static String LOCATION_X = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_X);
	private final static String LOCATION_Y = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_Y);

	public void setNodeRequest(NodeRequest nodeRequest) {
		this.nodeRequest = nodeRequest;
	}

	public void setAbsoluteLocation(Point location) {
		this.absoluteLocation = location;
	}

	@Override
	public String getLabel() {
		return "Create Node";
	}

	@Override
	public void execute() {
		final Point relativeLocation = this.absoluteLocation.getCopy();
		IFigure parentFigure = nodeRequest.getNodeGroupEditPart().getFigure();

		// Translate the location of the 'click'
		// to relative coordinates in the parent
		// coordinate system
		parentFigure.translateFromParent(relativeLocation);
		parentFigure.translateToRelative(relativeLocation);

		node = nodeRequest.getNodeGroup().createNode();
		node.getProperties().setInteger(LOCATION_X,
				BigInteger.valueOf(relativeLocation.x));
		node.getProperties().setInteger(LOCATION_Y,
				BigInteger.valueOf(relativeLocation.y));
	}

	@Override
	public void undo() {
		nodeRequest.getNodeGroup().removeNode(node);
		node = null;
	}

	public void redo() {
		this.execute();
	}
}