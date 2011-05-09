package de.dhbw.horb.ksm.xmlschema;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.xml.bind.JAXBException;

import org.junit.Test;
import org.mockito.Mockito;

import junit.framework.Assert;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.impl.KSMFactory;

public class TestNodeGroup {
	@Test
	public void testCreateNodeGroup() throws JAXBException {
		KSM ksm = KSMFactory.createEmptyKSM();
		Assert.assertNotNull(ksm);

		NodeGroup root = ksm.getNodeGroup();
		NodeGroup subNodeGroup = root.createNodeGroup();

		KSM ksmCopy = Utils.saveAndReload(ksm);
		Assert.assertEquals(root.getId(), ksmCopy.getNodeGroup().getId());

		Assert.assertEquals(ksm.getNodeGroup().getNodeGroups().size(), ksmCopy
				.getNodeGroup().getNodeGroups().size());
		Assert.assertEquals(ksm.getNodeGroup().getNodeGroups().get(0).getId(),
				subNodeGroup.getId());
	}

	@Test
	public void testRemoveNode() throws JAXBException {
		KSM ksm = KSMFactory.createEmptyKSM();
		Node node1 = ksm.getNodeGroup().createNode();
		Node node2 = ksm.getNodeGroup().createNode();
		Assert.assertTrue(ksm.getNodeGroup().removeNode(node1));
		Assert.assertEquals(1, ksm.getNodeGroup().getNodes().size());

		KSM ksmCopy = Utils.saveAndReload(ksm);
		Node lookupNode2 = ksmCopy.lookupNode(node2.getId());
		Assert.assertTrue(ksmCopy.getNodeGroup().removeNode(lookupNode2));
		Assert.assertEquals(0, ksmCopy.getNodeGroup().getNodes().size());
	}

	@Test
	public void testRemoveNodeGroup() throws JAXBException {
		KSM ksm = KSMFactory.createEmptyKSM();
		NodeGroup nodeGroup1 = ksm.getNodeGroup().createNodeGroup();
		NodeGroup nodeGroup2 = ksm.getNodeGroup().createNodeGroup();
		Assert.assertTrue(ksm.getNodeGroup().removeNodeGroup(nodeGroup1));
		Assert.assertEquals(1, ksm.getNodeGroup().getNodeGroups().size());

		KSM ksmCopy = Utils.saveAndReload(ksm);
		NodeGroup lookupNodeGroup2 = ksmCopy.lookupNodeGroup(nodeGroup2.getId());
		Assert.assertTrue(ksmCopy.getNodeGroup().removeNodeGroup(lookupNodeGroup2));
		Assert.assertEquals(0, ksmCopy.getNodeGroup().getNodes().size());
	}

	@Test
	public void testAddNode() throws JAXBException {
		KSM ksm = KSMFactory.createEmptyKSM();
		Node node = ksm.getNodeGroup().createNode();
		ksm.getNodeGroup().removeNode(node);
		ksm.getNodeGroup().addNode(node);

		Assert.assertEquals(1, ksm.getNodeGroup().getNodes().size());
	}

	@Test
	public void testAddNodeGroup() throws JAXBException {
		KSM ksm = KSMFactory.createEmptyKSM();
		NodeGroup nodeGroup = ksm.getNodeGroup().createNodeGroup();
		Assert.assertTrue(ksm.getNodeGroup().removeNodeGroup(nodeGroup));
		ksm.getNodeGroup().addNodeGroup(nodeGroup);

		Assert.assertEquals(1, ksm.getNodeGroup().getNodeGroups().size());
	}

	@Test
	public void testNodeGroupEvents() {
		PropertyChangeListener listenerMock = Mockito
				.mock(PropertyChangeListener.class);
		KSM ksm = KSMFactory.createEmptyKSM();
		NodeGroup nodeGroup = ksm.getNodeGroup();
		nodeGroup.addPropertyChangeListener(listenerMock);
		nodeGroup.createNode();
		NodeGroup nodeGroup2 = nodeGroup.createNodeGroup();
		Mockito.verify(listenerMock, Mockito.times(2)).propertyChange(
				Mockito.any(PropertyChangeEvent.class));

		Mockito.reset(listenerMock);
		nodeGroup2.addPropertyChangeListener(listenerMock);
		nodeGroup2.createNode();
		nodeGroup.createNodeGroup();
		Mockito.verify(listenerMock, Mockito.times(2)).propertyChange(
				Mockito.any(PropertyChangeEvent.class));
	}
}
