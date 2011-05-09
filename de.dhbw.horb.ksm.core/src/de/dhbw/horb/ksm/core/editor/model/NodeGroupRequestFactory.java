package de.dhbw.horb.ksm.core.editor.model;

import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.viewers.StructuredSelection;

import de.dhbw.horb.ksm.core.editor.parts.KSMEditPart;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeEditPart;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeGroupEditPart;
import de.dhbw.horb.ksm.core.editor.ui.DiagramEditor;

public class NodeGroupRequestFactory implements CreationFactory {
	private final DiagramEditor diagramEditor;

	public NodeGroupRequestFactory(DiagramEditor diagramEditor) {
		this.diagramEditor = diagramEditor;
	}

	@Override
	public Object getNewObject() {
		StructuredSelection selection = (StructuredSelection) diagramEditor
				.getSite().getSelectionProvider().getSelection();
		if (selection.getFirstElement() instanceof KSMNodeGroupEditPart) {
			// in selected NodeGroup
			KSMNodeGroupEditPart nodeGroupEditPart = (KSMNodeGroupEditPart) selection
					.getFirstElement();
			return new NodeGroupRequest(nodeGroupEditPart);
		} else if (selection.getFirstElement() instanceof KSMEditPart) {
			// In Root-NodeGropu
			KSMEditPart ksmEditPart = (KSMEditPart) selection.getFirstElement();
			return new NodeGroupRequest(ksmEditPart);
		} else if (selection.getFirstElement() instanceof KSMNodeEditPart) {
			// Create in this Node's Parent NodeGroup
			KSMNodeEditPart ksmNodeEditPart = (KSMNodeEditPart) selection.getFirstElement();
			// Parent on NodeEditPart is always NodeGroupEditPart
			return new NodeGroupRequest((KSMNodeGroupEditPart) ksmNodeEditPart.getParent());
		} else {
			System.out.println("Bad selection: " + selection);
			return null;
		}
	}

	@Override
	public Object getObjectType() {
		return NodeGroupRequest.class;
	}
}
