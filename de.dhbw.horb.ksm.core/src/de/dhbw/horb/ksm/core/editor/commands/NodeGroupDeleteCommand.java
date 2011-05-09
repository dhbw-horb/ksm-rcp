package de.dhbw.horb.ksm.core.editor.commands;

import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.model.api.NodeGroup;

public class NodeGroupDeleteCommand extends Command {
	private NodeGroup parentNodeGroup;
	private NodeGroup nodeGroup;

	public void setParentNodeGroup(NodeGroup nodeGroup) {
		this.parentNodeGroup = nodeGroup;
	}

	public void setNodeGroup(NodeGroup nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	@Override
	public String getLabel() {
		return "Delete Node";
	}

	@Override
	public void execute() {
		parentNodeGroup.removeNodeGroup(nodeGroup);
	}

	@Override
	public void undo() {
		parentNodeGroup.addNodeGroup(nodeGroup);
	}

	@Override
	public void redo() {
		this.execute();
	}
}