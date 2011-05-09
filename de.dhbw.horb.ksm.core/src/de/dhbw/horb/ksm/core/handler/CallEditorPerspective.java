package de.dhbw.horb.ksm.core.handler;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import de.dhbw.horb.ksm.core.perspectives.KSMEditorPerspective;

/**
 * Handler to open the KSM-Editor Perspective (no other exists atm)
 */
public class CallEditorPerspective extends AbstractHandler implements IHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow workbenchWindow;
		IWorkbenchPage activePage;
		IPerspectiveRegistry perspReg;
		IPerspectiveDescriptor perspId;

		workbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
		activePage = workbenchWindow.getActivePage();

		perspReg = workbenchWindow.getWorkbench().getPerspectiveRegistry();
		perspId = perspReg.findPerspectiveWithId(KSMEditorPerspective.ID);

		//Set Perspective
		activePage.setPerspective(perspId);
		return null;
	}
}
