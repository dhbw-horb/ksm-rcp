package de.dhbw.horb.tableeditor.perspective;


import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import de.dhbw.horb.tableeditor.view.TableEditorView;

public class TableEditorPerspective implements IPerspectiveFactory {
	public static final String ID = "ksm.tableeditor.perspectives.TableEditorPerspective"; //$NON-NLS-N$
	private IPageLayout factory;

	public TableEditorPerspective() {
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
		topRight.addView(TableEditorView.VIEW_ID);
//		topRight.addView("org.eclipse.ui.views.ContentOutline"); //$NON-NLS-N$

		// org.eclipse.ui.internal.PerspectiveBarNewContributionItem
	}
}