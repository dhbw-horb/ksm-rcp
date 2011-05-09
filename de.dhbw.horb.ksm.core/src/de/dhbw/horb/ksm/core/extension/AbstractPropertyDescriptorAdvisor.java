package de.dhbw.horb.ksm.core.extension;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;

/**
 * This class is intented to be subclassed to add extra properties to the model.
 *
 * See Extension Point de.dhbw.horb.ksm.core.model.property for details.
 *
 * TODO Boolean and Lists
 */
public abstract class AbstractPropertyDescriptorAdvisor {
	protected List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();

	public AbstractPropertyDescriptorAdvisor() {
		init();
	}

	protected abstract void init();

	public List<IPropertyDescriptor> getDescriptors() {
		return descriptors;
	}

	public void addProperty(String key, String caption) {
		String type = ModelProperties.INSTANCE.type(key);
		// String propertyName = ModelProperties.INSTANCE.name(key);
		if ("string".equals(type)) {
			descriptors.add(new TextPropertyDescriptor(key, caption));
		} else if ("decimal".equals(type)) {
			descriptors.add(new BigDecimalTextPropertyDescriptor(key, caption));
		} else if ("integer".equals(type)) {
			descriptors.add(new BigIntegerTextPropertyDescriptor(key, caption));
		} else if ("boolean".equals(type)) {
			// XXX
		} else if ("stringList".equals(type)) {
			// XXX
		} else if ("decimalList".equals(type)) {
			// XXX
		} else if ("integerList".equals(type)) {
			// XXX
		}
	}

	/*****/

	private class BigDecimalTextPropertyDescriptor extends
			TextPropertyDescriptor {
		ICellEditorValidator validator = new ICellEditorValidator() {
			@Override
			public String isValid(Object value) {
				try {
					new BigInteger((String) value);
					return null; // valid
				} catch (NumberFormatException e) {
					return "value must be a Integer value";
				}
			}
		};

		public BigDecimalTextPropertyDescriptor(Object id, String displayName) {
			super(id, displayName);
		}

		@Override
		protected ICellEditorValidator getValidator() {
			return validator;
		}
	}

	private class BigIntegerTextPropertyDescriptor extends
			TextPropertyDescriptor {
		ICellEditorValidator validator = new ICellEditorValidator() {
			@Override
			public String isValid(Object value) {
				try {
					new BigInteger((String) value);
					return null; // valid
				} catch (NumberFormatException e) {
					return "value must be a Integer value";
				}
			}
		};

		public BigIntegerTextPropertyDescriptor(Object id, String displayName) {
			super(id, displayName);
		}

		@Override
		protected ICellEditorValidator getValidator() {
			return validator;
		}
	}
}
