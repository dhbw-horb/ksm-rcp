package de.dhbw.horb.ksm.core.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.dhbw.horb.ksm.core.editor.commands.NodeDeleteCommand;
import de.dhbw.horb.ksm.core.editor.parts.KSMEditPart;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeGroupEditPart;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;

/**
 * Implements the action of deleting a node
 *
 */
public class NodeComponentEditPolicy extends ComponentEditPolicy {
	@Override
	protected Command createDeleteCommand(GroupRequest request) {
		NodeGroup nodeGroup;
		if (getHost().getParent() instanceof KSMNodeGroupEditPart) {
			KSMNodeGroupEditPart parent = (KSMNodeGroupEditPart) getHost()
					.getParent();
			nodeGroup = parent.getModel();
		} else if (getHost().getParent() instanceof KSMEditPart) {
			KSMEditPart parent = (KSMEditPart) getHost().getParent();
			nodeGroup = parent.getModel().getNodeGroup();
		} else {
			return null;
		}
		NodeDeleteCommand deleteCommand = new NodeDeleteCommand();
		deleteCommand.setNodeGroup(nodeGroup);
		deleteCommand.setNode((Node) getHost().getModel());
		return deleteCommand;
	}
}