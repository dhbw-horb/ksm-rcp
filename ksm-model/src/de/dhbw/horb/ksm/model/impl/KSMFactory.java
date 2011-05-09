package de.dhbw.horb.ksm.model.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;

import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.generated.ObjectFactory;
import de.dhbw.horb.ksm.model.generated.XKSM;

public class KSMFactory {
	private static JAXBContext jc = null;
	static ObjectFactory objectFactory = new ObjectFactory();

	static {
		try {
			jc = JAXBContext.newInstance(XKSM.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	public static KSM loadKSM(File file) throws FileNotFoundException,
			JAXBException {
		return loadKSM(new FileInputStream(file));
	}

	public static KSM loadKSM(InputStream is) throws JAXBException {
		Unmarshaller u = jc.createUnmarshaller();

		JAXBElement<XKSM> xksm  = u.unmarshal(new SAXSource(new InputSource(is)), XKSM.class);

		return new KSMImpl(xksm.getValue());
	}

	public static KSM createEmptyKSM() {
		ObjectFactory of = new ObjectFactory();
		XKSM xksm = of.createXKSM();
		KSMImpl ksm = new KSMImpl(xksm);
		return ksm;
	}

	public static void saveKSM(KSM ksm, File file)
			throws FileNotFoundException, JAXBException {
		saveKSM(ksm, new FileOutputStream(file));
	}

	public static void saveKSM(KSM ksm, OutputStream os) throws JAXBException {
		if (ksm instanceof KSMImpl) {
			KSMImpl ksmimpl = (KSMImpl) ksm;
			JAXBElement<XKSM> x = new JAXBElement<XKSM>(new QName("ksm"),
					XKSM.class, ksmimpl.getXksm());
			Marshaller m = jc.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(x, os);
		} else {
			throw new JAXBException("Illegal KSM Object type");
		}
	}
}
