package de.dhbw.horb.ksm.core.editor.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;

import de.dhbw.horb.ksm.core.editor.model.NodeGroupRequestFactory;
import de.dhbw.horb.ksm.core.editor.model.NodeRequestFactory;

public class DiagramTemplateTransferDropTargetListener extends
		TemplateTransferDropTargetListener {

	public DiagramTemplateTransferDropTargetListener(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	protected CreationFactory getFactory(Object template) {
		if (template instanceof NodeRequestFactory) {
			return (CreationFactory) template;
		} else if (template instanceof NodeGroupRequestFactory) {
			return (CreationFactory) template;
		} else {
			return null;
		}
	}
}