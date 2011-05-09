package de.dhbw.horb.ksm.core;


import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.ide.IDE;

import de.dhbw.horb.ksm.core.perspectives.KSMEditorPerspective;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

	public String getInitialWindowPerspectiveId() {
		return KSMEditorPerspective.ID;
	}

	public void initialize(IWorkbenchConfigurer configurer) {
		IDE.registerAdapters();
	}

	/**
	 * Return the Default Workspace of this Workbench.
	 * <br>
	 * used for Navigator View, defaults to null
	 */
	@Override
	public IAdaptable getDefaultPageInput() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		return workspace.getRoot();
	}
}
