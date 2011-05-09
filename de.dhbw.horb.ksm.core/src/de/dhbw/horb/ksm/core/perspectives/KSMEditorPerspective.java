package de.dhbw.horb.ksm.core.perspectives;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class KSMEditorPerspective implements IPerspectiveFactory {
	public static final String ID = "ksm.core.perspectives.KSMEditorPerspective"; //$NON-NLS-N$
	private IPageLayout factory;

	public KSMEditorPerspective() {
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

		IFolderLayout topRight = factory.createFolder("topRight",
				IPageLayout.RIGHT, 0.30f, factory.getEditorArea());
		topRight.addView("org.eclipse.ui.views.ContentOutline"); //$NON-NLS-N$

		IFolderLayout bottom = factory.createFolder("bottom",
				IPageLayout.BOTTOM, 0.60f, factory.getEditorArea());
		bottom.addView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-N$

		// org.eclipse.ui.internal.PerspectiveBarNewContributionItem
	}
}