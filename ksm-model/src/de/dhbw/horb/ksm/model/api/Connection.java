package de.dhbw.horb.ksm.model.api;

import java.beans.PropertyChangeListener;

public interface Connection {
	public String getTo();
	void setTo(String id);
	public Properties getProperties();
	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
}
