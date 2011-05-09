package de.dhbw.horb.ksm.model.api;

import java.util.List;

public interface KSM {
	/**
	 * Gibt die Wurzel-NodeGroup dieses Modells zurück.
	 *
	 * @return
	 */
	NodeGroup getNodeGroup();

	/**
	 * Schlägt das NodeGroup-Objekt mit dieser id nach.
	 *
	 * Durchläuft dazu alle NodeGroups im Modell
	 *
	 * @param id
	 * @return gesuchte NodeGroup oder null
	 */
	NodeGroup lookupNodeGroup(String id);

	/**
	 * Schlägt das Node-Objekt mit dieser id nach.
	 *
	 * Durchläuft dazu alle NodeGroups im Modell
	 *
	 * @param id
	 * @return gesuchte Node oder null
	 */
	Node lookupNode(String id);

	/**
	 * Gibt einen Iterator über alle Node Objekte im Model zurück.
	 *
	 * @return
	 */
	Iterable<Node> getAllNodes();

	/**
	 * gibt alle auf diesen Knoten zeigendenden Verbindungen
	 *
	 * @param n
	 *            zu überprüfender Knoten
	 * @return alle einkommenden Verbindungen.
	 */
	List<Node> getIncomingConnections(Node n);

	/**
	 * Gibt die mit diesem Modell verknüfpten Eigenschaften
	 *
	 * @return eigenschaften als Properites Object
	 */
	Properties getProperties();

	/**
	 * Gibt das version-Attribut
	 *
	 * @return version attribut des modells
	 */
	String getVersion();
}
