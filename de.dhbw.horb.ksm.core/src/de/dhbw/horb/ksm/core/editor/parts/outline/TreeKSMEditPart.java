package de.dhbw.horb.ksm.core.editor.parts.outline;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.ui.views.properties.IPropertySource;

import de.dhbw.horb.ksm.core.editor.model.property.ModelPropertySource;
import de.dhbw.horb.ksm.model.api.KSM;

@SuppressWarnings({ "rawtypes" })
public class TreeKSMEditPart extends AbstractTreeEditPart implements
		PropertyChangeListener {

	public TreeKSMEditPart(KSM model) {
		super(model);
	}

	@Override
	public Object getAdapter(Class key) {
		if (key.equals(IPropertySource.class)) {
			// Property Description for this Model-Object
			return new ModelPropertySource.KSMPropertySource(getModel());
		}
		return super.getAdapter(key);
	}

	protected List getModelChildren() {
		return Arrays.asList(getModel().getNodeGroup());
	}

	public void activate() {
		super.activate();
		getModel().getNodeGroup().addPropertyChangeListener(this);
	}

	public void deactivate() {
		getModel().getNodeGroup().removePropertyChangeListener(this);
		super.deactivate();
	}

	@Override
	public KSM getModel() {
		return (KSM) super.getModel();
	}

	public void propertyChange(PropertyChangeEvent change) {
		refreshChildren();
	}
}