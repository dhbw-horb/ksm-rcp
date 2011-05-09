package de.dhbw.horb.ksm.model.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.Properties;
import de.dhbw.horb.ksm.model.generated.XConnection;
import de.dhbw.horb.ksm.model.generated.XNode;

class NodeImpl implements Node {
	private static final String PROPERTY_CONNECTIONS = "connections";
	final XNode xnode;
	private final Properties properties;
	private List<Connection> connections = new ArrayList<Connection>();
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public NodeImpl(XNode xnode) {
		this.xnode = xnode;
		if (xnode.getConnections() == null) {
			xnode.setConnections(KSMFactory.objectFactory.createXConnections());
		}
		for (XConnection xconn : xnode.getConnections().getConnection()) {
			connections.add(new ConnectionImpl(xconn));
		}
		if (xnode.getProperties() == null) {
			xnode.setProperties(KSMFactory.objectFactory.createXProperties());
		}
		this.properties = new PropertiesImpl(xnode.getProperties());
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	@Override
	public List<Connection> getConnections() {
		return Collections.unmodifiableList(connections);
	}

	@Override
	public String getId() {
		return xnode.getId();
	}

	@Override
	public Properties getProperties() {
		return properties;
	}

	@Override
	public Connection createConnection(String to) {
		XConnection xConnection = KSMFactory.objectFactory.createXConnection();
		Connection connection = new ConnectionImpl(xConnection);
		connection.setTo(to);

		addConnection(connection);
		return connection;
	}

	@Override
	public Connection createConnection(Node toNode) {
		return createConnection(toNode.getId());
	}

	@Override
	public void addConnection(Connection connection) {
		if (connection instanceof ConnectionImpl) {
			ConnectionImpl c = (ConnectionImpl) connection;
			xnode.getConnections().getConnection().add(c.xconn);
			connections.add(connection);

			pcs.fireIndexedPropertyChange(PROPERTY_CONNECTIONS,
					connections.indexOf(connection), null, connection);
		} else {
			throw new IllegalArgumentException("Argument connection must be of type ConnectionImpl");
		}
	}

	@Override
	public boolean removeConnection(Connection connection) {
		if (connection instanceof ConnectionImpl) {
			int index = connections.indexOf(connection);
			ConnectionImpl c = (ConnectionImpl) connection;

			boolean ret = connections.remove(connection)
					&& xnode.getConnections().getConnection().remove(c.xconn);
			if (ret) {
				pcs.fireIndexedPropertyChange(PROPERTY_CONNECTIONS, index,
						connection, null);
				return true;
			} else {
				return false;
			}
		} else {
			throw new IllegalArgumentException("Argument connection must be of type ConnectionImpl");
		}
	}
}