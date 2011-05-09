package de.dhbw.horb.ksm.core.editor.model;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import de.dhbw.horb.ksm.core.editor.parts.KSMEditPart;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeGroupEditPart;
import de.dhbw.horb.ksm.model.api.NodeGroup;

/**
 * A {@link NodeGroupRequest} instance is created to track the request of
 * creating a new {@link NodeGroup} Object in the model.
 */
public class NodeGroupRequest {
	private final AbstractGraphicalEditPart nodeGroupEditPart;

	public NodeGroupRequest(KSMNodeGroupEditPart nodeGroupEditPart) {
		this.nodeGroupEditPart = nodeGroupEditPart;
	}

	public NodeGroupRequest(KSMEditPart ksmEditPart) {
		this.nodeGroupEditPart = ksmEditPart;
	}

	public NodeGroup getNodeGroup() {
		if (nodeGroupEditPart instanceof KSMEditPart) {
			KSMEditPart editPart = (KSMEditPart) nodeGroupEditPart;
			return editPart.getModel().getNodeGroup();
		} else {
			return (NodeGroup) nodeGroupEditPart.getModel();
		}
	}

	public AbstractGraphicalEditPart getEditPart() {
		return nodeGroupEditPart;
	}
}
