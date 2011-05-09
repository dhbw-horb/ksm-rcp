package de.dhbw.horb.ksm.core;

import org.eclipse.osgi.util.NLS;

public class KSMMessages extends NLS {
	private static final String BUNDLE_NAME = "de.dhbw.horb.ksm.core.messages"; //$NON-NLS-1$
	public static String ApplicationWorkbenchWindowAdvisor_Title;
	public static String PaletteFactory_Components;
	public static String PaletteFactory_Connection;
	public static String PaletteFactory_CreateConnection;
	public static String PaletteFactory_CreateNode;
	public static String PaletteFactory_Node;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, KSMMessages.class);
	}

	private KSMMessages() {
	}
}
