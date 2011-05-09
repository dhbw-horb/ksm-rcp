package de.dhbw.horb.ksm.core.editor.commands;

import java.math.BigInteger;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeEditPart;

public class NodeMoveCommand extends Command {
	private KSMNodeEditPart node;
	private Point relativeTargetLocation;
	private Point relativeOldLocation;

	private final static String LOCATION_X = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_X);
	private final static String LOCATION_Y = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_Y);

	public NodeMoveCommand(KSMNodeEditPart child) {
		this.node = child;
		relativeOldLocation = new Point(/**/
		/* X-Position */
		node.getModel().getProperties()
				.getInteger(LOCATION_X).intValue(),
		/* Y-Position */
		node.getModel().getProperties()
				.getInteger(LOCATION_Y).intValue());
	}

	public void setAbsoluteTargetLocation(Point absoluteTargetLocation) {
		relativeTargetLocation = absoluteTargetLocation.getCopy();
		node.getFigure().translateFromParent(relativeTargetLocation);
	}

	@Override
	public String getLabel() {
		return "Move Node";
	}

	@Override
	public void execute() {
		node.getModel()
				.getProperties()
				.setInteger(LOCATION_X,
						BigInteger.valueOf(relativeTargetLocation.x));
		node.getModel()
				.getProperties()
				.setInteger(LOCATION_Y,
						BigInteger.valueOf(relativeTargetLocation.y));
	}

	@Override
	public void undo() {
		node.getModel()
				.getProperties()
				.setInteger(LOCATION_X,
						BigInteger.valueOf(relativeOldLocation.x));
		node.getModel()
				.getProperties()
				.setInteger(LOCATION_Y,
						BigInteger.valueOf(relativeOldLocation.y));
	}

	@Override
	public void redo() {
		execute();
	}
}