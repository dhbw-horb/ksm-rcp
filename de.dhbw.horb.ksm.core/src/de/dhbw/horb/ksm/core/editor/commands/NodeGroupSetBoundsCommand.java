package de.dhbw.horb.ksm.core.editor.commands;

import java.math.BigInteger;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeGroupEditPart;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.api.Properties;

public class NodeGroupSetBoundsCommand extends Command {
	private NodeGroup nodeGroup;
	private Rectangle relativeOldBounds;
	private Rectangle relativeTargetBounds;

	private final static String LOCATION_X = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_LOCATION_X);
	private final static String LOCATION_Y = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_LOCATION_Y);
	private final static String HEIGHT = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_HEIGHT);
	private final static String WIDTH = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_WIDTH);

	public NodeGroupSetBoundsCommand(KSMNodeGroupEditPart child) {
		this.nodeGroup = child.getModel();
		Properties p = nodeGroup.getProperties();

		relativeOldBounds = new Rectangle(/**/
		/* X */
		p.getInteger(LOCATION_X).intValue(),
		/* Y */
		p.getInteger(LOCATION_Y).intValue(),
		/* width */
		p.getInteger(WIDTH).intValue(),
		/* height */
		p.getInteger(HEIGHT).intValue());
	}

	public void setRelativeTargetBounds(Rectangle rectangle) {
		relativeTargetBounds = rectangle.getCopy();
	}

	public void setNodeGroup(NodeGroup nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	@Override
	public String getLabel() {
		return "Move Node";
	}

	@Override
	public void execute() {
		nodeGroup.getProperties().setInteger(LOCATION_X,
				BigInteger.valueOf(relativeTargetBounds.x));
		nodeGroup.getProperties().setInteger(LOCATION_Y,
				BigInteger.valueOf(relativeTargetBounds.y));
		nodeGroup.getProperties().setInteger(WIDTH,
				BigInteger.valueOf(relativeTargetBounds.width));
		nodeGroup.getProperties().setInteger(HEIGHT,
				BigInteger.valueOf(relativeTargetBounds.height));
	}

	@Override
	public void undo() {
		nodeGroup.getProperties().setInteger(LOCATION_X,
				BigInteger.valueOf(relativeOldBounds.x));
		nodeGroup.getProperties().setInteger(LOCATION_Y,
				BigInteger.valueOf(relativeOldBounds.y));
		nodeGroup.getProperties().setInteger(WIDTH,
				BigInteger.valueOf(relativeOldBounds.width));
		nodeGroup.getProperties().setInteger(HEIGHT,
				BigInteger.valueOf(relativeOldBounds.height));
	}

	@Override
	public void redo() {
		execute();
	}
}