package de.dhbw.horb.simulator.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import de.dhbw.horb.simulator.view.SimulatorView;

public class SimulatorPerspective implements IPerspectiveFactory {
	public static final String ID = "ksm.simulator.perspectives.SimulatorPerspective"; //$NON-NLS-N$
	private IPageLayout factory;

	public SimulatorPerspective() {
		super();
	}

	public void createInitialLayout(IPageLayout factory) {
		this.factory = factory;
		factory.setEditorAreaVisible(true);
		addViews();
	}

	private void addViews() {
		IFolderLayout topLeft = factory.createFolder("topLeft", //$NON-NLS-N$
				IPageLayout.LEFT, 0.30f, factory.getEditorArea());

		topLeft.addView(de.dhbw.horb.ksm.core.view.Navigator.VIEW_ID);

		IFolderLayout topRight = factory.createFolder("bottom",
				IPageLayout.BOTTOM, 0.30f, factory.getEditorArea());
		topRight.addView(SimulatorView.VIEW_ID);
	}
}