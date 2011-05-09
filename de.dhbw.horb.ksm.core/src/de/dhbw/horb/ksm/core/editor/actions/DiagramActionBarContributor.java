package de.dhbw.horb.ksm.core.editor.actions;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.actions.ActionFactory;

/**
 * The Editor is linked with this class to contribute buttons to the toolbar.
 */
public class DiagramActionBarContributor extends ActionBarContributor {
	@Override
	protected void buildActions() {
		// Register GEF-Actions
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());
	}

	protected void declareGlobalActionKeys() {
		// do nothing
	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		// Add GEF-Actions to toolBar
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(getAction(ActionFactory.DELETE.getId()));

		// Zoom, see DiagrammEditor#getAdapter
		toolBarManager.add(new ZoomComboContributionItem(getPage()));
	}
}