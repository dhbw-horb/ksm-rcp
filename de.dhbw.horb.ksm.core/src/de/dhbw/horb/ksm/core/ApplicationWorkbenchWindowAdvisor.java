package de.dhbw.horb.ksm.core;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(700, 600));
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(false);
		configurer
				.setTitle(KSMMessages.ApplicationWorkbenchWindowAdvisor_Title);

		// Activate the Perspective Switcher
		configurer.setShowPerspectiveBar(true);
		// Position in the Eclipse-style top-right
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR,
				IWorkbenchPreferenceConstants.TOP_RIGHT);

		// XXX Should be dynamic, not easy since fragment-plugins doesnt have
		// activators
		PlatformUI
				.getPreferenceStore()
				.setValue(
						IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_EXTRAS,
						"ksm.tableeditor.perspectives.TableEditorPerspective,ksm.simulator.perspectives.SimulatorPerspective");
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.PERSPECTIVE_BAR_SIZE, 400);
	}
}
