package de.dhbw.horb.ksm.core.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import de.dhbw.horb.ksm.core.editor.commands.NodeRenameCommand;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeEditPart;

/**
 * This Policy controls the "click-to-edit" action of a Node-Figure
 *
 * @see KSMNodeEditPart#performRequest(org.eclipse.gef.Request)
 * @see KSMNodeEditPart#installEditPolicy(Object, org.eclipse.gef.EditPolicy)
 */
public class NodeDirectEditPolicy extends DirectEditPolicy {
	@Override
	protected Command getDirectEditCommand(DirectEditRequest request) {
		NodeRenameCommand command = new NodeRenameCommand(
				(KSMNodeEditPart) getHost());
		command.setNewCaption((String) request.getCellEditor().getValue());
		return command;
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		KSMNodeEditPart host2 = (KSMNodeEditPart) getHost();
		host2.getFigure().repaint();
	}
}