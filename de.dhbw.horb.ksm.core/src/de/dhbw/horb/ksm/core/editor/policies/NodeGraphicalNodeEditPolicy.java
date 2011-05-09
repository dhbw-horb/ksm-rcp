package de.dhbw.horb.ksm.core.editor.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import de.dhbw.horb.ksm.core.editor.commands.ConnectionCreateCommand;
import de.dhbw.horb.ksm.core.editor.commands.ConnectionReconnectCommand;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeEditPart;
import de.dhbw.horb.ksm.model.api.Node;

public class NodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	/** Execute at start of the connection procedure **/
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		ConnectionCreateCommand command = new ConnectionCreateCommand();
		command.setSource((Node) getHost().getModel());
		request.setStartCommand(command);
		return command;
	}

	/** Exececute when the user finished connecting **/
	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		ConnectionCreateCommand command = (ConnectionCreateCommand) request
				.getStartCommand();
		command.setTarget((Node) getHost().getModel());
		return command;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		ConnectionReconnectCommand command = new ConnectionReconnectCommand();
		command.setConnection(request.getConnectionEditPart());
		command.setOldTarget((KSMNodeEditPart) getHost());
		command.setTarget((KSMNodeEditPart) request.getTarget());
		return command;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		ConnectionReconnectCommand command = new ConnectionReconnectCommand();
		command.setConnection(request.getConnectionEditPart());
		command.setOldSource((KSMNodeEditPart) getHost());
		command.setSource((KSMNodeEditPart) request.getTarget());
		return command;
	}
}