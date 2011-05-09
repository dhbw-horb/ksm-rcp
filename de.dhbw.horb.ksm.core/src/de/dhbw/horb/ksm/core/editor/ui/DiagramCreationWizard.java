package de.dhbw.horb.ksm.core.editor.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;

import javax.xml.bind.JAXBException;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.FileEditorInput;

import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.impl.KSMFactory;

public class DiagramCreationWizard extends Wizard implements INewWizard {
	private WizardPage wizardPage;
	private IStructuredSelection selection;
	private IWorkbench workbench;

	// Abstract methods from IWorkbenchWizard
	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		this.workbench = workbench;
		this.selection = currentSelection;
	}

	// Overridden from Wizard
	@Override
	public void addPages() {
		// by default the superclass doesn't have any pages - here we add one
		this.wizardPage = new WizardPage(this.workbench, this.selection);
		addPage(this.wizardPage);
	}

	@Override
	public boolean performFinish() {
		return this.wizardPage.finish();
	}
}

class WizardPage extends WizardNewFileCreationPage {

	private IWorkbench workbench;

	public WizardPage(IWorkbench aWorkbench, IStructuredSelection selection) {
		super("ksmPage1", selection);
		this.setTitle("Create a Kybernetic Model");
		this.setDescription("Create a Kybernetic Model");
		this.workbench = aWorkbench;
	}

	public boolean finish() {
		IFile newFile = createNewFile();
		if (newFile == null)
			return false; // ie.- creation was unsuccessful

		// Since the file resource was created fine, open it for editing
		// iff requested by the user
		try {
			IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = dwindow.getActivePage();
			FileEditorInput fileEditorInput = new FileEditorInput(newFile);
			if (page != null)
				page.openEditor(fileEditorInput, DiagramEditor.EDITOR_ID);
		} catch (org.eclipse.ui.PartInitException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		Calendar c = Calendar.getInstance();
		String postfix = c.get(Calendar.DAY_OF_MONTH) + "-"
				+ c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR);
		this.setFileName("ksm-model-" + postfix + ".ksm");
		setPageComplete(validatePage());
	}

	protected InputStream getInitialContents() {
		// by default the superclass returns null for this
		KSM createEmptyKSM = KSMFactory.createEmptyKSM();
		ByteArrayOutputStream os = new ByteArrayOutputStream(256);
		try {
			KSMFactory.saveKSM(createEmptyKSM, os);
		} catch (JAXBException e) {
			// XXX use Logger
			e.printStackTrace();
			return null;
		}
		return new ByteArrayInputStream(os.toByteArray());
	}
}