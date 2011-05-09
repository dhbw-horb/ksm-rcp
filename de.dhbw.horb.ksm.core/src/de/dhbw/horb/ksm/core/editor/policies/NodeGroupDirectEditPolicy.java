package de.dhbw.horb.ksm.core.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import de.dhbw.horb.ksm.core.editor.commands.NodeGroupRenameCommand;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeGroupEditPart;

/**
 * This Policy controls the "click-to-edit" action of a NodeGroup-Figure.
 *
 * @see KSMNodeGroupEditPart#performRequest(org.eclipse.gef.Request)
 * @see KSMNodeGroupEditPart#installEditPolicy(Object,
 *      org.eclipse.gef.EditPolicy)
 */
public class NodeGroupDirectEditPolicy extends DirectEditPolicy {
	private final KSMNodeGroupEditPart ksmNodeGroupEditPart;

	public NodeGroupDirectEditPolicy(KSMNodeGroupEditPart ksmNodeGroupEditPart) {
		this.ksmNodeGroupEditPart = ksmNodeGroupEditPart;
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
	}

	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		String newCaption = (String) request.getCellEditor().getValue();
		NodeGroupRenameCommand command = new NodeGroupRenameCommand(
				this.ksmNodeGroupEditPart);
		command.setNewCaption(newCaption);
		return command;
	}
}