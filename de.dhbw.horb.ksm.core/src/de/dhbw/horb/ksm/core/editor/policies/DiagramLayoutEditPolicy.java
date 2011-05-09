package de.dhbw.horb.ksm.core.editor.policies;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import de.dhbw.horb.ksm.core.editor.commands.NodeCreateCommand;
import de.dhbw.horb.ksm.core.editor.commands.NodeGroupCreateCommand;
import de.dhbw.horb.ksm.core.editor.commands.NodeGroupSetBoundsCommand;
import de.dhbw.horb.ksm.core.editor.commands.NodeMoveCommand;
import de.dhbw.horb.ksm.core.editor.model.NodeGroupRequest;
import de.dhbw.horb.ksm.core.editor.model.NodeRequest;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeEditPart;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeGroupEditPart;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;

/**
 * The Layout Policy constrains figures inside the applied figure by a location
 * and size.
 */
public class DiagramLayoutEditPolicy extends XYLayoutEditPolicy {

	/**
	 * This method returns if the given Child is resizable or not
	 */
	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		if (child instanceof KSMNodeEditPart) {
			// Nodes are not resizeable
			return new NonResizableEditPolicy();
		} else if (child instanceof KSMNodeGroupEditPart) {
			// But NodeGroups are
			return new ResizableEditPolicy();
		} else {
			return new NonResizableEditPolicy();
		}
	}

	@Override
	protected Command createAddCommand(EditPart child, Object constraint) {
		return null; // no support for adding
	}

	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		if (child.getModel() instanceof Node) {
			NodeMoveCommand locationCommand = new NodeMoveCommand(
					(KSMNodeEditPart) child);
			locationCommand.setAbsoluteTargetLocation(((Rectangle) constraint)
					.getLocation());
			return locationCommand;
		} else if (child.getModel() instanceof NodeGroup) {
			Rectangle rectange = (Rectangle) constraint;
			NodeGroupSetBoundsCommand command = new NodeGroupSetBoundsCommand(
					(KSMNodeGroupEditPart) child);
			command.setRelativeTargetBounds(rectange);
			return command;
		} else {
			System.out.println("createChangeConstraintCommand for unknown child: " + child);
		}
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest createRequest) {
		if (createRequest.getNewObjectType() == NodeRequest.class) {
			NodeRequest nodeRequest = (NodeRequest) createRequest
					.getNewObject();
			if (nodeRequest != null) {
				NodeCreateCommand command = new NodeCreateCommand();
				command.setNodeRequest(nodeRequest);
				command.setAbsoluteLocation(createRequest.getLocation());
				return command;
			}
		} else if (createRequest.getNewObjectType() == NodeGroupRequest.class) {
			NodeGroupRequest nodeGroupRequest = (NodeGroupRequest) createRequest
					.getNewObject();
			if (nodeGroupRequest != null) {
				NodeGroupCreateCommand command = new NodeGroupCreateCommand();
				command.setNodeGroupRequest(nodeGroupRequest);
				command.setAbsoluteLocation(createRequest.getLocation());
				return command;
			}
		}

		return UnexecutableCommand.INSTANCE;
	}

	// @Override
	// protected Command createChangeConstraintCommand(
	// ChangeBoundsRequest request, EditPart child, Object constraint) {
	// if (child.getModel() instanceof NodeGroup) {
	// Rectangle rectange = (Rectangle) constraint;
	// NodeGroup nodeGroup = (NodeGroup) child.getModel();
	// NodeGroupResizeCommand command = new NodeGroupResizeCommand();
	// command.setNodeGroup(nodeGroup);
	// command.setRectange(rectange);
	//
	// return command;
	// } else {
	// return super.createChangeConstraintCommand(request, child,
	// constraint);
	// }
	// }

	protected Command getDeleteDependantCommand(Request request) {
		System.out
				.println("DiagramLayoutEditPolicy.getDeleteDependantCommand()");
		return null; // no support for deleting a dependant
	}

}