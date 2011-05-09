package de.dhbw.horb.ksm.core.editor.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;

/**
 * this Factory make EditParts from the Data Model root Object
 * {@link KSMDiagramModel}.
 */
public class PartFactory implements EditPartFactory {
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part;

		if (model instanceof KSM) {
			part = new KSMEditPart();
		} else if (model instanceof NodeGroup) {
			part = new KSMNodeGroupEditPart();
		} else if (model instanceof Node) {
			part = new KSMNodeEditPart();
		} else if (model instanceof Connection) {
			part = new KSMConnectionEditPart();
		} else {
			return null;
		}

		part.setModel(model);
		return part;
	}
}