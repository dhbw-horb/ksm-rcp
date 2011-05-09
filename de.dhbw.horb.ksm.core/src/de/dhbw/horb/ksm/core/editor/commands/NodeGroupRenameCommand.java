package de.dhbw.horb.ksm.core.editor.commands;

import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeGroupEditPart;
import de.dhbw.horb.ksm.model.api.NodeGroup;

/**
 * This command change the visual-name of a NodeGroup
 *
 * @see NodeRenameCommand
 */
public class NodeGroupRenameCommand extends Command {
	private final NodeGroup nodeGroup;
	private String newCaption;
	private String oldCaption;

	public NodeGroupRenameCommand(KSMNodeGroupEditPart child) {
		this.nodeGroup = child.getModel();
	}

	public void setNewCaption(String newCaption) {
		this.newCaption = newCaption;
	}

	@Override
	public String getLabel() {
		return "Move Node";
	}

	@Override
	public void execute() {
		oldCaption = nodeGroup
				.getProperties()
				.getString(
						ModelProperties.INSTANCE
								.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_CAPTION));
		redo();
	}

	@Override
	public void undo() {
		nodeGroup
				.getProperties()
				.setString(
						ModelProperties.INSTANCE
								.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_CAPTION),
						oldCaption);
	}

	@Override
	public void redo() {
		nodeGroup
				.getProperties()
				.setString(
						ModelProperties.INSTANCE
								.stripType(ModelProperties.INSTANCE.NODEGROUP_VISUAL_CAPTION),
						newCaption);
	}
}