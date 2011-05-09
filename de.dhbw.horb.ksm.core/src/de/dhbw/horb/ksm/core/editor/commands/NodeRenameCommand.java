package de.dhbw.horb.ksm.core.editor.commands;

import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeEditPart;
import de.dhbw.horb.ksm.model.api.Node;

/**
 * This command change the visual-name of a Node.
 *
 * @see NodeGroupRenameCommand
 */
public class NodeRenameCommand extends Command {
	private final Node nodeGroup;
	private String newCaption;
	private String oldCaption;

	private final static String CAPTION = ModelProperties.INSTANCE
			.stripType(ModelProperties.INSTANCE.NODE_VISUAL_CAPTION);

	public NodeRenameCommand(KSMNodeEditPart child) {
		this.nodeGroup = child.getModel();
	}

	public void setNewCaption(String newCaption) {
		this.newCaption = newCaption;
	}

	@Override
	public String getLabel() {
		return "Rename Node";
	}

	@Override
	public void execute() {
		oldCaption = nodeGroup.getProperties().getString(CAPTION);
		redo();
	}

	@Override
	public void undo() {
		nodeGroup.getProperties().setString(CAPTION, oldCaption);
	}

	@Override
	public void redo() {
		nodeGroup.getProperties().setString(CAPTION, newCaption);
	}
}