package de.dhbw.horb.ksm.xmlschema;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import de.dhbw.horb.ksm.model.api.Connection;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.NodeGroup;
import de.dhbw.horb.ksm.model.api.Properties;
import de.dhbw.horb.ksm.model.impl.KSMFactory;

public class TestProperties extends TestCase {
	@Test
	public void testStringDecimalIntegerWithSameName() throws JAXBException {
		KSM myKSM = KSMFactory.createEmptyKSM();
		NodeGroup rootNodeGroup = myKSM.getNodeGroup();
		Properties properties = rootNodeGroup.getProperties();
		properties.setString("testbla", "blabla");
		properties.setDecimal("testbla", new BigDecimal(123.213123));
		properties.setInteger("testbla", new BigInteger("123123"));
		properties.setBoolean("testbla", false);
		KSM myKSMCopy = Utils.saveAndReload(myKSM);
		Assert.assertEquals(properties.getString("testbla"), myKSMCopy
				.getNodeGroup().getProperties().getString("testbla"));
		Assert.assertEquals(properties.getDecimal("testbla"), myKSMCopy
				.getNodeGroup().getProperties().getDecimal("testbla"));
		Assert.assertEquals(properties.getInteger("testbla"), myKSMCopy
				.getNodeGroup().getProperties().getInteger("testbla"));
	}

	@Test
	public void testListProperties() throws JAXBException {
		KSM myKSM = KSMFactory.createEmptyKSM();
		NodeGroup rootNodeGroup = myKSM.getNodeGroup();
		Properties properties = rootNodeGroup.getProperties();
		{
			List<BigDecimal> decimalList = properties
					.createDecimalList("meine.Liste");
			Assert.assertNotNull(decimalList);
			Assert.assertNull(properties.createDecimalList("meine.Liste"));
		}
		{
			List<String> stringList = properties
					.createStringList("meine.stringListe");
			Assert.assertNotNull(stringList);
			Assert.assertNull(properties.createStringList("meine.stringListe"));
		}
		{
			properties.removeStringList("meine.stringListe");
			System.out.println(properties.getStringList("meine.stringListe"));
			Assert.assertTrue(properties.removeDecimalList("meine.Liste"));
		}
	}

	@Test
	public void testPropertiesKSMNodeNodeGroupConnection() {
		KSM ksm = KSMFactory.createEmptyKSM();
		Assert.assertNotNull(ksm.getProperties());

		NodeGroup nodeGroup = ksm.getNodeGroup();
		Assert.assertNotNull(nodeGroup.getProperties());

		Node node = nodeGroup.createNode();
		Assert.assertNotNull(node.getProperties());

		Node node2 = nodeGroup.createNode();
		Connection connection = node.createConnection(node2);
		Assert.assertNotNull(connection.getProperties());
	}

	@Test
	public void testPropertiesEvents() {
		PropertyChangeListener listenerMock = Mockito
				.mock(PropertyChangeListener.class);
		KSM ksm = KSMFactory.createEmptyKSM();
		Properties properties = ksm.getProperties();

		properties.addPropertyChangeListener(listenerMock);
		Mockito.reset(listenerMock);
		properties.setString("a", "b");
		Mockito.verify(listenerMock).propertyChange(Mockito.any(PropertyChangeEvent.class));

		Mockito.reset(listenerMock);
		properties.setDecimal("a", new BigDecimal(5));
		Mockito.verify(listenerMock).propertyChange(Mockito.any(PropertyChangeEvent.class));

		Mockito.reset(listenerMock);
		properties.setInteger("a", BigInteger.valueOf(1000));
		Mockito.verify(listenerMock).propertyChange(Mockito.any(PropertyChangeEvent.class));

		Mockito.reset(listenerMock);
		properties.setBoolean("a", true);
		Mockito.verify(listenerMock).propertyChange(Mockito.any(PropertyChangeEvent.class));

		Mockito.reset(listenerMock);
		properties.createStringList("foo");
		Mockito.verify(listenerMock).propertyChange(Mockito.any(PropertyChangeEvent.class));

		Mockito.reset(listenerMock);
		properties.createDecimalList("foo");
		Mockito.verify(listenerMock).propertyChange(Mockito.any(PropertyChangeEvent.class));

		Mockito.reset(listenerMock);
		properties.createIntegerList("foo");
		Mockito.verify(listenerMock).propertyChange(Mockito.any(PropertyChangeEvent.class));
	}
}
