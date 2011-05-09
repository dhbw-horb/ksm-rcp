package de.dhbw.horb.ksm.core.editor.commands;

import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;

public class NodeDeleteCommand extends Command {
	private NodeGroup nodeGroup;
	private Node node;

	public void setNodeGroup(NodeGroup nodeGroup) {
		this.nodeGroup = nodeGroup;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@Override
	public String getLabel() {
		return "Delete Node";
	}

	@Override
	public void execute() {
		nodeGroup.removeNode(node);
	}

	@Override
	public void undo() {
		nodeGroup.addNode(node);
	}

	@Override
	public void redo() {
		this.execute();
	}
}