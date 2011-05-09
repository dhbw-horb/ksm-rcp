package de.dhbw.horb.ksm.core.editor.commands;

import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.Node;

public class ConnectionDeleteCommand extends Command {

	protected Connection connection;
	protected Node sourceNode;
	protected Node targetNode;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setSourceNode(Node sourceNode) {
		this.sourceNode = sourceNode;
	}

	@Override
	public String getLabel() {
		return "Delete Connection";
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		sourceNode.removeConnection(connection);
	}

	@Override
	public void undo() {
		sourceNode.addConnection(connection);
	}

	@Override
	public void redo() {
		execute();
	}
}