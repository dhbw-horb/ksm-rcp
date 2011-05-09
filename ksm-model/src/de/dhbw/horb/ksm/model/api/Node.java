package de.dhbw.horb.ksm.model.api;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface Node {
	String getId();

	Properties getProperties();

	/**
	 * Erzeugt eine Neue Verbindung.
	 *
	 * Der Aufrufer muss dafür Sorge tragen, dass das Ziel gesetzt wird.
	 *
	 * @return neue Verbindung (Connection Object)
	 */
	Connection createConnection(String to);

	/**
	 * @see Node#createConnection(String)
	 */
	Connection createConnection(Node toNode);

	boolean removeConnection(Connection connection);

	/**
	 * Nur lesbare Liste von Verbindungen
	 *
	 * @return
	 */
	List<Connection> getConnections();

	void addPropertyChangeListener(PropertyChangeListener listener);

	void removePropertyChangeListener(PropertyChangeListener listener);

	void addConnection(Connection connection);
}
