package de.dhbw.horb.ksm.core.editor.commands;

import java.math.BigInteger;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.model.NodeGroupRequest;
import de.dhbw.horb.ksm.model.api.NodeGroup;

public class NodeGroupCreateCommand extends Command {
	private NodeGroupRequest nodeGroupRequest;
	private NodeGroup nodeGroup = null;
	private Point absoluteLocation;

	private final static String LOCATION_X = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_LOCATION_X);
	private final static String LOCATION_Y = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_LOCATION_Y);
	private final static String HEIGHT = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_HEIGHT);
	private final static String WIDTH = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_WIDTH);

	public void setNodeGroupRequest(NodeGroupRequest nodeGroupRequest) {
		this.nodeGroupRequest = nodeGroupRequest;
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
		final IFigure parentFigure = nodeGroupRequest.getEditPart().getFigure();

		// Translate the location of the 'click'
		// to relative coordinates in the parent
		// coordinate system
		parentFigure.translateToRelative(relativeLocation);
		parentFigure.translateFromParent(relativeLocation);

		nodeGroup = nodeGroupRequest.getNodeGroup().createNodeGroup();
		nodeGroup.getProperties().setInteger(LOCATION_X,
				BigInteger.valueOf(relativeLocation.x));
		nodeGroup.getProperties().setInteger(LOCATION_Y,
				BigInteger.valueOf(relativeLocation.y));

		nodeGroup.getProperties().setInteger(WIDTH, BigInteger.valueOf(150));
		nodeGroup.getProperties().setInteger(HEIGHT, BigInteger.valueOf(100));
	}

	@Override
	public void undo() {
		nodeGroupRequest.getNodeGroup().removeNodeGroup(nodeGroup);
	}

	public void redo() {
		nodeGroupRequest.getNodeGroup().addNodeGroup(nodeGroup);
	}
}