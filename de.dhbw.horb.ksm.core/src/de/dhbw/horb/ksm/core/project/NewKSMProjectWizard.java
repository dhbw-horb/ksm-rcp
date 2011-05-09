package de.dhbw.horb.ksm.core.project;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * Create a new KSM Project and add KSM Project Nature.
 *
 * <a href="http://www.eclipse.org/articles/Article-Builders/builders.html"
 * >link1</a>.
 *
 * <a href=
 * "http://help.eclipse.org/helios/index.jsp?topic=/org.eclipse.platform.doc.isv/guide/resAdv_natures.htm"
 * >link2</a>
 */
public class NewKSMProjectWizard extends BasicNewProjectResourceWizard {
	@Override
	public boolean performFinish() {
		if (super.performFinish()) {
			IProject project = getNewProject();
			try {
				IProjectDescription description = project.getDescription();
				String[] natures = description.getNatureIds();
				String[] newNatures = new String[natures.length + 1];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				newNatures[natures.length] = "de.dhbw.horb.ksm.core.ksmProjectNature";
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			return true;
		} else
			return false;
	}
}
