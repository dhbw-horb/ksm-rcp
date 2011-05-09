package de.dhbw.horb.ksm.core.editor.model.property;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColorCellEditor;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.extension.AbstractPropertyDescriptorAdvisor;

/**
 * Add Basic NodeProperties.
 */
public class BaseNodePropertyAdvisor extends AbstractPropertyDescriptorAdvisor {

	@Override
	protected void init() {
		addProperty(ModelProperties.INSTANCE.NODE_VISUAL_CAPTION, "Caption");

		descriptors.add(new KSMColorPropertyDescriptor(
				ModelProperties.INSTANCE.NODE_VISUAL_COLOR, "Color"));
	}

	private class KSMColorPropertyDescriptor extends PropertyDescriptor {
		public KSMColorPropertyDescriptor(Object id, String displayName) {
			super(id, displayName);
		}

		@Override
		public CellEditor createPropertyEditor(Composite parent) {
			CellEditor editor = new KSMColorCellEditor(parent);
			return editor;
		}
	}

	private class KSMColorCellEditor extends ColorCellEditor {

		public KSMColorCellEditor(Composite parent) {
			super(parent);
		}

		@Override
		protected Object openDialogBox(Control cellEditorWindow) {
			ColorDialog dialog = new ColorDialog(cellEditorWindow.getShell());
			Object value = getValue();
			if (value instanceof String) {
				dialog.setRGB(ModelProperties.INSTANCE.stringToRGB((String) value));
			}
			value = dialog.open();
			return ModelProperties.INSTANCE.rgbToString(dialog.getRGB());
		}


		@Override
		protected void updateContents(Object value) {
			if (value instanceof String) {
				super.updateContents(ModelProperties.INSTANCE.stringToRGB((String) value));
			}
		}
	}
}
