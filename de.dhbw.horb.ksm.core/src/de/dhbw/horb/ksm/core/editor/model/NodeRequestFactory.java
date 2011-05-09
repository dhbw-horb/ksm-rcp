package de.dhbw.horb.ksm.core.editor.model;

import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.jface.viewers.StructuredSelection;

import de.dhbw.horb.ksm.core.editor.parts.KSMEditPart;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeGroupEditPart;
import de.dhbw.horb.ksm.core.editor.ui.DiagramEditor;

public class NodeRequestFactory implements CreationFactory {
	private final DiagramEditor diagramEditor;

	public NodeRequestFactory(DiagramEditor diagramEditor) {
		this.diagramEditor = diagramEditor;
	}

	@Override
	public Object getNewObject() {
		StructuredSelection selection = getCurrentSelection();
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof KSMNodeGroupEditPart) {
			KSMNodeGroupEditPart nodeGroupEditPart = (KSMNodeGroupEditPart) firstElement;
			return new NodeRequest(nodeGroupEditPart);
		} else if (firstElement instanceof KSMEditPart) {
			// in case the root (<KSM>) is "selected"
			KSMEditPart ksmEditPart = (KSMEditPart) firstElement;
			return new NodeRequest(ksmEditPart);
		} else {
			// Selection is not applicable for creating a Node inside
			return null;
		}
	}

	private StructuredSelection getCurrentSelection() {
		return (StructuredSelection) diagramEditor.getSite()
				.getSelectionProvider().getSelection();
	}

	@Override
	public Object getObjectType() {
		return NodeRequest.class;
	}
}
