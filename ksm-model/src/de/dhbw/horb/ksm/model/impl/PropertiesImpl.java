package de.dhbw.horb.ksm.model.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import de.dhbw.horb.ksm.model.api.Properties;
import de.dhbw.horb.ksm.model.generated.XProperties;
import de.dhbw.horb.ksm.model.generated.XPropertyBoolean;
import de.dhbw.horb.ksm.model.generated.XPropertyDecimal;
import de.dhbw.horb.ksm.model.generated.XPropertyDecimalList;
import de.dhbw.horb.ksm.model.generated.XPropertyInteger;
import de.dhbw.horb.ksm.model.generated.XPropertyIntegerList;
import de.dhbw.horb.ksm.model.generated.XPropertyString;
import de.dhbw.horb.ksm.model.generated.XPropertyStringList;

class PropertiesImpl implements Properties {

	private final XProperties xproperties;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public PropertiesImpl(XProperties properties) {
		this.xproperties = properties;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	private XPropertyString getStringXProperty(String name) {
		if (name == null)
			return null;
		if (xproperties.getString() == null)
			return null;
		for (XPropertyString x : xproperties.getString()) {
			if (name.equals(x.getName())) {
				return x;
			}
		}
		return null;
	}

	private XPropertyDecimal getDecimalXProperty(String name) {
		if (name == null)
			return null;
		if (xproperties.getDecimal() == null)
			return null;
		for (XPropertyDecimal x : xproperties.getDecimal()) {
			if (name.equals(x.getName())) {
				return x;
			}
		}
		return null;
	}

	private XPropertyInteger getIntegerXProperty(String name) {
		if (name == null)
			return null;
		if (xproperties.getInteger() == null)
			return null;
		for (XPropertyInteger x : xproperties.getInteger()) {
			if (name.equals(x.getName())) {
				return x;
			}
		}
		return null;
	}

	private XPropertyBoolean getBooleanXProperty(String name) {
		if (name == null)
			return null;
		if (xproperties.getBoolean() == null)
			return null;
		for (XPropertyBoolean x : xproperties.getBoolean()) {
			if (name.equals(x.getName())) {
				return x;
			}
		}
		return null;
	}

	@Override
	public String getString(String name) {
		XPropertyString stringProperty = getStringXProperty(name);
		if (stringProperty == null) {
			return null;
		} else {
			return (String) stringProperty.getValue();
		}
	}

	@Override
	public BigDecimal getDecimal(String name) {
		XPropertyDecimal decimalProperty = getDecimalXProperty(name);
		if (decimalProperty == null) {
			return null;
		} else {
			return (BigDecimal) decimalProperty.getValue();
		}
	}

	@Override
	public BigInteger getInteger(String name) {
		XPropertyInteger integerProperty = getIntegerXProperty(name);
		if (integerProperty == null) {
			return null;
		} else {
			return integerProperty.getValue();
		}
	}

	@Override
	public Boolean getBoolean(String name) {
		XPropertyBoolean booleanProperty = getBooleanXProperty(name);
		if (booleanProperty == null) {
			return null;
		} else {
			return booleanProperty.isValue();
		}
	}

	private XPropertyDecimalList getDecimalListXProperty(String name) {
		if (name == null)
			return null;
		for (XPropertyDecimalList x : xproperties.getDecimalList()) {
			if (name.equals(x.getName())) {
				return x;
			}
		}
		return null;
	}

	@Override
	public List<BigDecimal> getDecimalList(String name) {
		XPropertyDecimalList xDecimalList = getDecimalListXProperty(name);
		if (xDecimalList == null)
			return null;
		else
			return xDecimalList.getValue();
	}

	@Override
	public void setString(final String name, final String value) {
		XPropertyString stringProperty = getStringXProperty(name);
		if (stringProperty == null) {
			stringProperty = KSMFactory.objectFactory.createXPropertyString();
			stringProperty.setName(name);
			xproperties.getString().add(stringProperty);
			stringProperty.setValue(value);
			pcs.firePropertyChange("string:" + name, null, value);
		} else {
			Object oldValue = stringProperty.getValue();
			stringProperty.setValue(value);
			pcs.firePropertyChange("string:" + name, oldValue, value);
		}
	}

	@Override
	public void setDecimal(final String name, final BigDecimal value) {
		XPropertyDecimal property = getDecimalXProperty(name);
		if (property == null) {
			property = KSMFactory.objectFactory.createXPropertyDecimal();
			property.setName(name);
			xproperties.getDecimal().add(property);
			property.setValue(value);
			pcs.firePropertyChange("decimal:" + name, null, value);
		} else {
			BigDecimal oldValue = property.getValue();
			property.setValue(value);
			pcs.firePropertyChange("decimal:" + name, oldValue, value);
		}
	}

	@Override
	public void setInteger(String name, BigInteger value) {
		XPropertyInteger property = getIntegerXProperty(name);
		if (property == null) {
			property = KSMFactory.objectFactory.createXPropertyInteger();
			property.setName(name);
			xproperties.getInteger().add(property);
			property.setValue(value);
			pcs.firePropertyChange("integer:" + name, null, value);
		} else {
			BigInteger oldValue = property.getValue();
			property.setValue(value);
			pcs.firePropertyChange("integer:" + name, oldValue, value);
		}

	}

	@Override
	public void setBoolean(String name, Boolean value) {
		XPropertyBoolean property = getBooleanXProperty(name);
		if (property == null) {
			property = KSMFactory.objectFactory.createXPropertyBoolean();
			property.setName(name);
			xproperties.getBoolean().add(property);
			property.setValue(value);
			pcs.firePropertyChange("boolean:" + name, null, value);
		} else {
			Boolean oldValue = property.isValue();
			property.setValue(value);
			pcs.firePropertyChange("boolean:" + name, oldValue, value);
		}

	}

	private XPropertyStringList getStringListXProperty(String name) {
		if (name == null)
			return null;
		for (XPropertyStringList x : xproperties.getStringList()) {
			if (name.equals(x.getName())) {
				return x;
			}
		}
		return null;
	}

	public XPropertyIntegerList getIntegerListXProperty(String name) {
		if (name == null)
			return null;
		for (XPropertyIntegerList x : xproperties.getIntegerList()) {
			if (name.equals(x.getName())) {
				return x;
			}
		}
		return null;
	}

	@Override
	public List<BigInteger> getIntegerList(String name) {
		XPropertyIntegerList xIntegerList = getIntegerListXProperty(name);
		if (xIntegerList == null)
			return null;
		else
			return xIntegerList.getValue();
	}

	@Override
	public List<String> getStringList(String name) {
		XPropertyStringList xStringList = getStringListXProperty(name);
		if (xStringList == null)
			return null;
		else
			return xStringList.getValue();
	}

	/**
	 *
	 * @return null if name==null or property already exists
	 */
	@Override
	public List<String> createStringList(String name) {
		if (name == null)
			return null;
		if (getStringList(name) != null) {
			return null;
		}
		XPropertyStringList list = KSMFactory.objectFactory
				.createXPropertyStringList();
		list.setName(name);
		xproperties.getStringList().add(list);
		pcs.fireIndexedPropertyChange("stringList" + name, xproperties
				.getStringList().indexOf(list), null, list.getValue());
		return list.getValue();
	}

	/**
	 *
	 * @return null if name==null or property already exists
	 */
	@Override
	public List<BigDecimal> createDecimalList(String name) {
		if (name == null)
			return null;
		if (getDecimalList(name) != null) {
			return null;
		}
		XPropertyDecimalList list = KSMFactory.objectFactory
				.createXPropertyDecimalList();
		list.setName(name);
		xproperties.getDecimalList().add(list);
		pcs.fireIndexedPropertyChange("decimalList" + name, xproperties
				.getDecimalList().indexOf(list), null, list.getValue());
		return list.getValue();
	}

	/**
	 *
	 * @return null if name==null or property already exists
	 */
	@Override
	public List<BigInteger> createIntegerList(String name) {
		if (name == null)
			return null;
		if (getIntegerList(name) != null) {
			return null;
		}
		XPropertyIntegerList list = KSMFactory.objectFactory
				.createXPropertyIntegerList();
		list.setName(name);
		xproperties.getIntegerList().add(list);
		pcs.fireIndexedPropertyChange("integerList" + name, xproperties
				.getIntegerList().indexOf(list), null, list.getValue());
		return list.getValue();
	}

	@Override
	public boolean removeStringList(String name) {
		XPropertyStringList oldValue = getStringListXProperty(name);
		int index = xproperties.getStringList().indexOf(oldValue);
		boolean ret = xproperties.getStringList().remove(oldValue);
		if (ret) {
			pcs.fireIndexedPropertyChange("stringList" + name, index, oldValue,
					null);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeDecimalList(String name) {
		XPropertyDecimalList oldValue = getDecimalListXProperty(name);
		int index = xproperties.getDecimalList().indexOf(oldValue);
		boolean ret = xproperties.getDecimalList().remove(oldValue);
		if (ret) {
			pcs.fireIndexedPropertyChange("decimalList", index, oldValue, null);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeIntegerList(String name) {
		XPropertyIntegerList oldValue = getIntegerListXProperty(name);
		int index = xproperties.getDecimalList().indexOf(oldValue);
		boolean ret = xproperties.getDecimalList().remove(oldValue);
		if (ret) {
			pcs.fireIndexedPropertyChange("integerList", index, oldValue, null);
			return true;
		} else {
			return false;
		}
	}
}