package de.dhbw.horb.tableeditor.view;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.api.Properties;

public class PropertyLabelProvider extends ColumnLabelProvider {
	private final String propertyName;

	public PropertyLabelProvider(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String getText(Object element) {
		String value = null;
		Properties properties = null;
		if (element instanceof KSM) {
			properties = ((KSM) element).getProperties();
		} else if (element instanceof NodeGroup) {
			properties = ((NodeGroup) element).getProperties();
		} else if (element instanceof Node) {
			properties = ((Node) element).getProperties();
		} else if (element instanceof Connection) {
			properties = ((Connection) element).getProperties();
		}

		if (properties != null) {
			String type = ModelProperties.INSTANCE.type(propertyName);
			String name = ModelProperties.INSTANCE.name(propertyName);
			if ("string".equals(type)) {
				value = properties.getString(name);
				if (value == null) {
					value = "<null>";
				}
			} else if ("decimal".equals(type)) {
				BigDecimal bigDecimal = properties.getDecimal(name);
				if (bigDecimal == null) {
					value = "<null>";
				} else {
					value = bigDecimal.toPlainString();
				}
			} else if ("integer".equals(type)) {
				BigInteger bigInteger = properties.getInteger(name);
				if (bigInteger == null) {
					value = "<null>";
				} else {
					value = bigInteger.toString();
				}
			} else if ("boolean".equals(type)) {
				// TODO nullchecks...
				value = properties.getBoolean(name).toString();
			} else if ("stringList".equals(type)) {
				value = properties.getStringList(name).toString();
			} else if ("decimalList".equals(type)) {
				value = properties.getDecimalList(name).toString();
			} else if ("integerList".equals(type)) {
				value = properties.getIntegerList(name).toString();
			}
		}
		if (value == null) {
			return "<null>";
		} else {
			return value;
		}
	}
}
