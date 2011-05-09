package de.dhbw.horb.ksm.xmlschema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBException;

import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.impl.KSMFactory;

public class Utils {
	static KSM saveAndReload(KSM ksm ) throws JAXBException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		KSMFactory.saveKSM(ksm, os);
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		return KSMFactory.loadKSM(is);
	}
}
