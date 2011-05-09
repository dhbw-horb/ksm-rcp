package de.dhbw.horb.ksm.core.editor.model;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import de.dhbw.horb.ksm.core.editor.parts.KSMEditPart;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeGroupEditPart;
import de.dhbw.horb.ksm.model.api.NodeGroup;

/**
 * Only used to track a node request
 */
public class NodeRequest {
	private final NodeGroup nodeGroup;
	private final AbstractGraphicalEditPart nodeGroupEditPart;

	public NodeRequest(KSMNodeGroupEditPart nodeGroupEditPart) {
		this.nodeGroupEditPart = nodeGroupEditPart;
		this.nodeGroup = nodeGroupEditPart.getModel();
		;
	}

	public NodeRequest(KSMEditPart ksmEditPart) {
		this.nodeGroupEditPart = ksmEditPart;
		this.nodeGroup = ksmEditPart.getModel().getNodeGroup();
	}

	public NodeGroup getNodeGroup() {
		return nodeGroup;
	}

	public AbstractGraphicalEditPart getNodeGroupEditPart() {
		return nodeGroupEditPart;
	}
}
