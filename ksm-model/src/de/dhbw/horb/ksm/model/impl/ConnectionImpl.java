package de.dhbw.horb.ksm.model.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.Properties;
import de.dhbw.horb.ksm.model.generated.XConnection;

class ConnectionImpl implements Connection {
	final XConnection xconn;
	private final Properties properties;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public ConnectionImpl(XConnection xconn) {
		this.xconn = xconn;
		if (xconn.getProperties() == null) {
			xconn.setProperties(KSMFactory.objectFactory.createXProperties());
		}
		this.properties = new PropertiesImpl(xconn.getProperties());
	}

	@Override
	public String getTo() {
		return xconn.getTo();
	}

	@Override
	public void setTo(String id) {
		String oldValue = getTo();
		xconn.setTo(id);
		pcs.firePropertyChange("to", oldValue, getTo());
	}

	@Override
	public Properties getProperties() {
		return properties;
	}
}
