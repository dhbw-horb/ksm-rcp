package de.dhbw.horb.ksm.model.api;

import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface Properties {
	/**
	 * Get Simple String Property by Name
	 */
	String getString(String name);

	/**
	 * Get Simple Decimal Property by Name
	 */
	BigDecimal getDecimal(String name);

	/**
	 * Get Simple Integer Property by Name
	 */
	BigInteger getInteger(String name);

	/**
	 * Get simple Boolean Property by name
	 */
	Boolean getBoolean(String name);

	/**
	 * Set Simple String Property by Name.
	 *
	 * fires PropertyChange Event with name "string:NAME" where NAME is
	 * Parameter name
	 */
	void setString(String name, String value);

	/**
	 * Set Simple Decimal Property by Name
	 *
	 * fires PropertyChange Event with name "decimal:NAME" where NAME is
	 * Parameter name
	 */
	void setDecimal(String name, BigDecimal value);

	/**
	 * Set Simple Integer Property by Name
	 *
	 * fires PropertyChange Event with name "integer:NAME" where NAME is
	 * Parameter name
	 */
	void setInteger(String name, BigInteger value);

	/**
	 * Set simple Boolean Property by Name
	 *
	 * fires PropertyChange Event with name "boolean:NAME" where NAME is
	 * Parameter name
	 */
	void setBoolean(String string, Boolean b);

	/**
	 * get StringList Property by name
	 */
	List<String> getStringList(String name);

	/**
	 * get DecimalList Property by name
	 */
	List<BigDecimal> getDecimalList(String name);

	/**
	 * get IntegerList Property by name
	 */
	List<BigInteger> getIntegerList(String name);

	/**
	 * Create new StringList Property
	 *
	 * fires PropertyChange Event with name "stringList:NAME" where NAME is
	 * parameter name and Index is the Position in the list
	 */
	List<String> createStringList(String name);

	/**
	 * Create new DecimalList Property
	 *
	 * fires PropertyChange Event with name "decimalList:NAME" where NAME is
	 * parameter name and Index is the Position in the list
	 */
	List<BigDecimal> createDecimalList(String name);

	/**
	 * Create new IntegerList Property
	 *
	 * fires PropertyChange Event with name "integerList:NAME" where NAME is
	 * parameter name and Index is the Position in the list
	 */
	List<BigInteger> createIntegerList(String name);

	/**
	 * Remove StringList Property
	 *
	 * fire PropertyChange Event with name "stringList:NAME" where NAME is
	 * the parameter name
	 */
	boolean removeStringList(String name);

	/**
	 * Remove DecimalList Property
	 *
	 * fire PropertyChange Event with name "decimalList:NAME" where NAME is
	 * the parameter name
	 */
	boolean removeDecimalList(String name);

	/**
	 * Remove IntegerList Property
	 *
	 *
	 * fire PropertyChange Event with name "integerList:NAME" where NAME is
	 * the parameter name
	 */
	boolean removeIntegerList(String name);

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