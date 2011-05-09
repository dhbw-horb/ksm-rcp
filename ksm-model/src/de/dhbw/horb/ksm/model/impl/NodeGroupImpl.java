package de.dhbw.horb.ksm.model.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.generated.XNode;
import de.dhbw.horb.ksm.model.generated.XNodeGroup;

class NodeGroupImpl implements NodeGroup {

	private final XNodeGroup xnodegroup;
	private final List<Node> nodes = new ArrayList<Node>();
	private final List<NodeGroup> nodeGroups = new ArrayList<NodeGroup>();
	private final PropertiesImpl properties;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	public NodeGroupImpl(XNodeGroup xnodegroup) {
		this.xnodegroup = xnodegroup;
		if (xnodegroup.getProperties() == null) {
			xnodegroup.setProperties(KSMFactory.objectFactory
					.createXProperties());
		}
		this.properties = new PropertiesImpl(xnodegroup.getProperties());
		for (XNode xnode : xnodegroup.getNode()) {
			nodes.add(new NodeImpl(xnode));
		}
		for (XNodeGroup xOtherNodeGroup : xnodegroup.getNodegroup()) {
			nodeGroups.add(new NodeGroupImpl(xOtherNodeGroup));
		}
	}

	@Override
	public List<Node> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	@Override
	public List<NodeGroup> getNodeGroups() {
		return Collections.unmodifiableList(nodeGroups);
	}

	@Override
	public Node createNode() {
		XNode xNode = KSMFactory.objectFactory.createXNode();
		xNode.setId(UUID.randomUUID().toString());
		NodeImpl node = new NodeImpl(xNode);
		addNode(node);
		return node;
	}

	@Override
	public void addNode(Node node) {
		if (node instanceof NodeImpl) {
			NodeImpl nodeImpl = (NodeImpl) node;
			xnodegroup.getNode().add(nodeImpl.xnode);
			nodes.add(nodeImpl);
			pcs.fireIndexedPropertyChange("nodes", nodes.indexOf(nodeImpl),
					null, node);
		} else {
			throw new IllegalArgumentException(
					"The node Object must be of type NodeImpl");
		}
	}

	@Override
	public void addNodeGroup(NodeGroup nodeGroup) {
		if (nodeGroup instanceof NodeGroupImpl) {
			NodeGroupImpl nodeGruopImpl = (NodeGroupImpl) nodeGroup;
			xnodegroup.getNodegroup().add(nodeGruopImpl.xnodegroup);
			nodeGroups.add(nodeGruopImpl);
			pcs.fireIndexedPropertyChange("nodegroups",
					nodes.indexOf(nodeGruopImpl), null, nodeGroup);
		} else {
			throw new IllegalArgumentException(
					"The nodeGroup Object must be of type NodeGroupImpl");
		}
	}

	@Override
	public boolean removeNode(Node node) {
		if (node instanceof NodeImpl) {
			NodeImpl nodeImpl = (NodeImpl) node;
			int index = nodes.indexOf(node);
			boolean success = nodes.remove(node)
					&& xnodegroup.getNode().remove(nodeImpl.xnode);
			if (success) {
				pcs.fireIndexedPropertyChange("nodes", index, node, null);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public NodeGroup createNodeGroup() {
		XNodeGroup subXNodeGroup = KSMFactory.objectFactory.createXNodeGroup();
		subXNodeGroup.setId(UUID.randomUUID().toString());

		NodeGroup nodeGroup = new NodeGroupImpl(subXNodeGroup);
		addNodeGroup(nodeGroup);
		return nodeGroup;
	}

	@Override
	public boolean removeNodeGroup(NodeGroup nodeGroup) {
		if (nodeGroup instanceof NodeGroupImpl) {
			NodeGroupImpl nodeGroupImpl = (NodeGroupImpl) nodeGroup;
			int index = nodeGroups.indexOf(nodeGroupImpl);
			boolean success = nodeGroups.remove(nodeGroupImpl);
			success &= xnodegroup.getNodegroup().remove(
					nodeGroupImpl.xnodegroup);
			if (success) {
				pcs.fireIndexedPropertyChange("nodegroups", index, nodeGroup,
						null);
				return true;
			} else {
				return false;
			}
		} else {
			throw new IllegalArgumentException(
					"The nodeGroup Object must be of type NodeGroupImpl");
		}
	}

	@Override
	public String getId() {
		return xnodegroup.getId();
	}

	@Override
	public PropertiesImpl getProperties() {
		return properties;
	}
}