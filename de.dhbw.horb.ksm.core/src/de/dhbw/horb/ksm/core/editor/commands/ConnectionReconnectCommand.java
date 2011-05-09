package de.dhbw.horb.ksm.core.editor.commands;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;

import de.dhbw.horb.ksm.core.editor.parts.KSMNodeEditPart;

/**
 * This command reconnects source or target of connection
 */
@SuppressWarnings("unused")
public class ConnectionReconnectCommand extends Command {

	private ConnectionEditPart connectionEditPart;
	private KSMNodeEditPart newSourceNodeEditPart = null;
	private KSMNodeEditPart newTargetNodeEditPart = null;
	private KSMNodeEditPart prevTargetNodeEditPart = null;
	private KSMNodeEditPart prevSourceNodeEditPart = null;

	public void setSource(KSMNodeEditPart source) {
		this.newSourceNodeEditPart = source;
	}

	public void setOldSource(KSMNodeEditPart oldSourceNodeEditPart) {
		this.prevSourceNodeEditPart = oldSourceNodeEditPart;
	}

	public void setConnection(ConnectionEditPart connectionEditPart) {
		this.connectionEditPart = connectionEditPart;
	}

	public void setTarget(KSMNodeEditPart target) {
		this.newTargetNodeEditPart = target;
	}

	public void setOldTarget(KSMNodeEditPart oldTargetNodeEditPart) {
		this.prevTargetNodeEditPart = oldTargetNodeEditPart;
	}

	@Override
	public String getLabel() {
		// TODO be more verbose
		return "Reconnect Connection";
	}

	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public void execute() {
		System.out.println("ConnectionReconnectCommand.execute()");
		// KSMConnectionModel connectionModel = (KSMConnectionModel)
		// connectionEditPart
		// .getModel();
		// if (newSourceNodeEditPart != null && prevSourceNodeEditPart != null)
		// {
		// KSMNodeModel sourceModel = (KSMNodeModel) newSourceNodeEditPart
		// .getModel();
		// connectionModel.setSource(sourceModel);
		// newSourceNodeEditPart.refresh();
		// prevSourceNodeEditPart.refresh();
		// }
		// if (newTargetNodeEditPart != null && prevTargetNodeEditPart != null)
		// {
		// KSMNodeModel targetModel = (KSMNodeModel) newTargetNodeEditPart
		// .getModel();
		// connectionModel.setTarget(targetModel);
		// newTargetNodeEditPart.refresh();
		// prevTargetNodeEditPart.refresh();
		// }
	}

	@Override
	public void undo() {
		System.out.println("ConnectionReconnectCommand.undo()");
		// KSMConnectionModel connectionModel = (KSMConnectionModel)
		// connectionEditPart
		// .getModel();
		// if (newSourceNodeEditPart != null && prevSourceNodeEditPart != null)
		// {
		// KSMNodeModel sourceModel = (KSMNodeModel) prevSourceNodeEditPart
		// .getModel();
		// connectionModel.setSource(sourceModel);
		// prevSourceNodeEditPart.refresh();
		// newSourceNodeEditPart.refresh();
		// }
		// if (newTargetNodeEditPart != null && prevTargetNodeEditPart != null)
		// {
		// KSMNodeModel targetModel = (KSMNodeModel) prevTargetNodeEditPart
		// .getModel();
		// connectionModel.setTarget(targetModel);
		// prevTargetNodeEditPart.refresh();
		// newTargetNodeEditPart.refresh();
		// }
	}

	@Override
	public void redo() {
		execute();
	}
}