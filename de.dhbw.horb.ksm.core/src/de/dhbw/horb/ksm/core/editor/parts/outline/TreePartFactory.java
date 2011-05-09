package de.dhbw.horb.ksm.core.editor.parts.outline;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;

// TreePart used in Outline Page
public class TreePartFactory implements EditPartFactory {

	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part;
		if (model instanceof KSM) {
			part = new TreeKSMEditPart((KSM) model);
		} else if (model instanceof NodeGroup) {
			part = new TreeNodeGroupEditPart((NodeGroup) model);
		} else if (model instanceof Node) {
			part = new TreeNodeEditPart((Node) model);
		} else if (model instanceof Connection) {
			System.out.println("TreePartFactory.createEditPart()");
			return null;
		} else {
			System.out.println("TreePartFactory.createEditPart() "+ model );
			return null;
		}
		return part;
	}
}