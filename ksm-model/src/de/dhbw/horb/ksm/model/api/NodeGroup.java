package de.dhbw.horb.ksm.model.api;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface NodeGroup {
	/**
	 * @return <b>unmodifiable</b> list of all nodes in this NodeGroup
	 */
	List<Node> getNodes();

	/**
	 * @return <b>unmodifiable</b> list of all nodegroups in this nodegroup
	 */
	List<NodeGroup> getNodeGroups();

	/**
	 * Create new Node in this NodeGroup.
	 *
	 * fire indexed PropertyChange Event with name "nodes" and index of this new
	 * node.
	 */
	Node createNode();

	/**
	 * Add a, previously created, Node to this NodeGroup.
	 *
	 *
	 * fire indexed PropertyChange Event with name "nodes" and index of this new
	 * node.
	 */
	void addNode(Node node);

	/**
	 * Create new NodeGroup in this NodeGroup.
	 *
	 * fire indexed PropertyChange Event with name "nodegroups" and index of
	 * this new nodegroup.
	 */
	NodeGroup createNodeGroup();

	/**
	 * Add a, previously created, NodeGroup to this NodeGroup.
	 *
	 *
	 * fire indexed PropertyChange Event with name "nodegroups" and index of this new
	 * node.
	 */
	void addNodeGroup(NodeGroup nodeGroup);

	/**
	 * @return id attribute of this nodegroup
	 */
	String getId();

	/**
	 * All properties of this node-group
	 */
	Properties getProperties();

	/**
	 * Remove node from this nodegroup.
	 *
	 * fire indexed propertychangeevent with propertyname nodes
	 *
	 * @return true if operation was successful
	 */
	boolean removeNode(Node node);

	/**
	 * Remove a sub-{@link NodeGroup} from this {@link NodeGroup}.
	 *
	 * fire indexed propertychange event with propertyname nodegroups.
	 *
	 * @param nodeGroup
	 *            a nodeGroup reference
	 */
	boolean removeNodeGroup(NodeGroup nodeGroup);

	/**
	 * Register new PropertyChangeListener.
	 *
	 * @param listener
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Remove previously registered PropertyChangeListener
	 *
	 * @param listener
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);

}
