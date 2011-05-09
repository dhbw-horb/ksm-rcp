package de.dhbw.horb.ksm.xmlschema;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;
import org.mockito.Mockito;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.impl.KSMFactory;

public class TestConnection {
	@Test
	public void testConnectionEvents() {
		PropertyChangeListener listenerMock = Mockito
				.mock(PropertyChangeListener.class);

		KSM ksm = KSMFactory.createEmptyKSM();
		NodeGroup nodeGroup = ksm.getNodeGroup();
		Node node = nodeGroup.createNode();
		node.addPropertyChangeListener(listenerMock);
		Connection connection = node.createConnection("bla");
		node.removeConnection(connection);
		Mockito.verify(listenerMock, Mockito.times(2)).propertyChange(
				Mockito.any(PropertyChangeEvent.class));
	}
}
