package de.dhbw.horb.ksm.core.editor.commands;

import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.Node;

public class ConnectionCreateCommand extends Command {
	protected Connection connection;
	protected Node source, target;

	public void setSource(Node source) {
		this.source = source;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	@Override
	public String getLabel() {
		return "Create Connection";
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		connection = source.createConnection(target);
	}

	@Override
	public void undo() {
		source.removeConnection(connection);
	}

	@Override
	public void redo() {
		source.addConnection(connection);
	}
}