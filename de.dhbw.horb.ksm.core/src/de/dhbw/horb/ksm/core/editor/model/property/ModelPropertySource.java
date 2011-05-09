package de.dhbw.horb.ksm.core.editor.model.property;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.extension.AbstractPropertyDescriptorAdvisor;
import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.api.Properties;

/**
 * TODO other types than string
 */
public abstract class ModelPropertySource implements IPropertySource {
	private final String type;

	ModelPropertySource(final String type) {
		this.type = type;
	}

	protected abstract Properties getProperties();

	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		ArrayList<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
				.getExtensionPoint("de.dhbw.horb.ksm.core.model.property");
		if (extensionPoint == null) {
			return new IPropertyDescriptor[] {};
		}

		IConfigurationElement[] config = extensionPoint
				.getConfigurationElements();

		try {
			for (IConfigurationElement e : config) {
				if (type.equals(e.getAttribute("type"))) {
					Object execClass = e.createExecutableExtension("class");

					if (execClass instanceof AbstractPropertyDescriptorAdvisor) {
						AbstractPropertyDescriptorAdvisor ex = (AbstractPropertyDescriptorAdvisor) execClass;
						descriptors.addAll(ex.getDescriptors());
					}
				}
			}
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return descriptors.toArray(new IPropertyDescriptor[] {});
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof String) {
			String type = ModelProperties.INSTANCE.type((String) id);
			String name = ModelProperties.INSTANCE.name((String) id);
			if (type.equals("string")) {
				return getProperties().getString(name);
			}
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return getPropertyValue(id) != null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		System.out.println("reset " + id);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id instanceof String) {
			String type = ModelProperties.INSTANCE.type((String) id);
			String name = ModelProperties.INSTANCE.name((String) id);
			if (type.equals("string") && value instanceof String) {
				getProperties().setString(name, (String) value);
			}
		}
	}

	/**
	 * Specialized type for {@link KSM} Model-Objects
	 */
	public static class KSMPropertySource extends ModelPropertySource {
		private final KSM model;

		public KSMPropertySource(final KSM model) {
			super("ksm");
			this.model = model;
		}

		@Override
		protected Properties getProperties() {
			return model.getProperties();
		}
	}

	/**
	 * Specialized type for {@link NodeGroup} Model-Objects
	 */
	public static class NodeGroupPropertySource extends ModelPropertySource {
		private final NodeGroup model;

		public NodeGroupPropertySource(final NodeGroup model) {
			super("nodegroup");
			this.model = model;
		}

		@Override
		protected Properties getProperties() {
			return model.getProperties();
		}
	}

	public static class NodePropertySource extends ModelPropertySource {
		private final Node model;

		public NodePropertySource(final Node model) {
			super("node");
			this.model = model;
		}

		@Override
		protected Properties getProperties() {
			return model.getProperties();
		}
	}

	public static class ConnectionPropertySource extends ModelPropertySource {
		private final Connection model;

		public ConnectionPropertySource(final Connection model) {
			super("connection");
			this.model = model;
		}

		@Override
		protected Properties getProperties() {
			return model.getProperties();
		}
	}
}
