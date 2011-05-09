package de.dhbw.horb.ksm.xmlschema;

import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.impl.KSMFactory;

public class TestKSM {
	@Test
	public void testLookup() throws JAXBException {
		KSM myKSM = KSMFactory.createEmptyKSM();
		Node node1 = myKSM.getNodeGroup().createNode();
		NodeGroup nodeGroup1 = myKSM.getNodeGroup().createNodeGroup();
		Node node2 = nodeGroup1.createNode();

		Assert.assertSame(node1, myKSM.lookupNode(node1.getId()));
		Assert.assertSame(node2, myKSM.lookupNode(node2.getId()));

		KSM reloadedKSM = Utils.saveAndReload(myKSM);
		Assert.assertEquals(node1.getId(), reloadedKSM
				.lookupNode(node1.getId()).getId());

		Assert.assertEquals(node2.getId(), reloadedKSM
				.lookupNode(node2.getId()).getId());

		Assert.assertSame(nodeGroup1, myKSM.lookupNodeGroup(nodeGroup1.getId()));
		Assert.assertEquals(nodeGroup1.getId(),
				reloadedKSM.lookupNodeGroup(nodeGroup1.getId()).getId());
	}

	@Test
	public void testIncomingConnections() {
		KSM myKSM = KSMFactory.createEmptyKSM();
		Node node1 = myKSM.getNodeGroup().createNode();
		Node node2 = myKSM.getNodeGroup().createNode();
		@SuppressWarnings("unused")
		Connection connection = node1.createConnection(node2);
		Node incomingNode = myKSM.getIncomingConnections(node2).get(0);
		Assert.assertSame(node1, incomingNode);
	}
}