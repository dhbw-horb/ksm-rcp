package de.dhbw.horb.ksm.xmlschema;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.impl.KSMFactory;

public class TestNode {
	private static final String PROP_VALUE = "hello-world";
	private static final String PROP_NAME = "visual.name";

	@Test
	public void testNodeAndStringProperty() throws JAXBException {
		KSM ksm = KSMFactory.createEmptyKSM();
		Assert.assertNotNull(ksm);

		NodeGroup nodeGroup = ksm.getNodeGroup();
		Node node = nodeGroup.createNode();
		node.getProperties().setString(PROP_NAME, PROP_VALUE);

		NodeGroup createNodeGroup = nodeGroup.createNodeGroup();
		createNodeGroup.createNode();

		KSM ksmCopy = Utils.saveAndReload(ksm);

		Assert.assertEquals(1, ksmCopy.getNodeGroup().getNodes().size());
		Assert.assertEquals(PROP_VALUE, ksmCopy.getNodeGroup().getNodes()
				.get(0).getProperties().getString(PROP_NAME));
	}

	@Test
	public void testNodeConnectionsCreate() throws JAXBException {
		KSM ksm = KSMFactory.createEmptyKSM();
		Node node1 = ksm.getNodeGroup().createNode();
		Node node2 = ksm.getNodeGroup().createNode();

		@SuppressWarnings("unused")
		Connection connection = node1.createConnection(node2);

		String to = node1.getConnections().get(0).getTo();
		Assert.assertSame(node2, ksm.lookupNode(to));

		KSM ksmCopy = Utils.saveAndReload(ksm);
		Assert.assertEquals(node2.getId(), ksmCopy.lookupNode(to).getId());
	}

	@Test
	public void testNodeConnectionsRemove() {
		KSM ksm = KSMFactory.createEmptyKSM();
		Node node1 = ksm.getNodeGroup().createNode();
		Node node2 = ksm.getNodeGroup().createNode();

		Connection connection = node1.createConnection(node2);
		Assert.assertEquals(1, node1.getConnections().size());

		Assert.assertTrue(node1.removeConnection(connection));
		Assert.assertFalse(node1.removeConnection(connection));
		Assert.assertFalse(node1.removeConnection(connection));
		Assert.assertEquals(0, node1.getConnections().size());
		Assert.assertEquals(0, ksm.getIncomingConnections(node2).size());
	}

	@Test
	public void testNodeGroupEvents() {
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