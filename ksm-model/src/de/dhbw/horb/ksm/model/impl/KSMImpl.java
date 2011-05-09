package de.dhbw.horb.ksm.model.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.generated.XKSM;
import de.dhbw.horb.ksm.model.generated.XNodeGroup;

class KSMImpl implements KSM {
	private final XKSM xksm;
	private NodeGroupImpl nodeGroup;
	private PropertiesImpl properties;

	public KSMImpl(XKSM xksm) {
		this.xksm = xksm;
		if (xksm.getNodegroup() == null) {
			XNodeGroup xNodeGroup = KSMFactory.objectFactory.createXNodeGroup();
			xNodeGroup.setId(UUID.randomUUID().toString());
			xksm.setNodegroup(xNodeGroup);
		}
		this.nodeGroup = new NodeGroupImpl(xksm.getNodegroup());
		if (xksm.getProperties() == null) {
			xksm.setProperties(KSMFactory.objectFactory.createXProperties());
		}
		this.properties = new PropertiesImpl(xksm.getProperties());
	}

	XKSM getXksm() {
		return xksm;
	}

	@Override
	public NodeGroup getNodeGroup() {
		return nodeGroup;
	}

	@Override
	public PropertiesImpl getProperties() {
		return properties;
	}

	@Override
	public String getVersion() {
		return xksm.getVersion();
	}

	@Override
	public NodeGroup lookupNodeGroup(String id) {
		if (id == null)
			return null;

		ArrayDeque<NodeGroup> queue = new ArrayDeque<NodeGroup>();
		queue.add(getNodeGroup());
		while (!queue.isEmpty()) {
			NodeGroup group = queue.pop();
			if (group.getId().equals(id)) {
				return group;
			} else {
				queue.addAll(group.getNodeGroups());
			}
		}
		return null;
	}

	@Override
	public Node lookupNode(String id) {
		if (id == null)
			return null;

		for (Node node : getAllNodes()) {
			if (id.equals(node.getId())) {
				return node;
			}
		}
		return null;
	}

	@Override
	public Iterable<Node> getAllNodes() {
		List<Node> nodes = new ArrayList<Node>();
		ArrayDeque<NodeGroup> queue = new ArrayDeque<NodeGroup>();
		queue.add(getNodeGroup());
		while (!queue.isEmpty()) {
			NodeGroup group = queue.pop();
			nodes.addAll(group.getNodes());
			queue.addAll(group.getNodeGroups());
		}
		return nodes;
	}

	@Override
	public List<Node> getIncomingConnections(Node node) {
		List<Node> nodes = new ArrayList<Node>();
		for (Node otherNode : getAllNodes()) {
			for (Connection connection : otherNode.getConnections()) {
				if (connection.getTo().equals(node.getId())) {
					nodes.add(otherNode);
				}
			}
		}
		return nodes;
	}
}