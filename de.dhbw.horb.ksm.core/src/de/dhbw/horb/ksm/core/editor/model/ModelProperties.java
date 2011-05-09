package de.dhbw.horb.ksm.core.editor.model;

import org.eclipse.swt.graphics.RGB;

public enum ModelProperties {
	INSTANCE;

	public String NODE_VISUAL_LOCATION_X = "integer:visual.location.x";//$NON-NLS-N$
	public String NODE_VISUAL_LOCATION_Y = "integer:visual.location.y";//$NON-NLS-N$
	public String NODE_VISUAL_CAPTION = "string:visual.caption";//$NON-NLS-N$
	public String NODE_VISUAL_COLOR = "string:visual.color";//$NON-NLS-N$

	public String NODEGROUP_VISUAL_LOCATION_X = "integer:visual.location.x";//$NON-NLS-N$
	public String NODEGROUP_VISUAL_LOCATION_Y = "integer:visual.location.y";//$NON-NLS-N$
	public String NODEGROUP_VISUAL_WIDTH = "integer:visual.width";//$NON-NLS-N$
	public String NODEGROUP_VISUAL_HEIGHT = "integer:visual.height";//$NON-NLS-N$
	public String NODEGROUP_VISUAL_CAPTION = "string:visual.caption";//$NON-NLS-N$

	public String CONNECTION_VISUAL_CAPTION = "integer:visual.caption";//$NON-NLS-N$
	public String CONNECTION_DATA_FUNCTIONTYPE = "string:data.functionType";//$NON-NLS-N$
	public String CONNECTION_DATA_FUNCTION = "decimalList:data.functionType";//$NON-NLS-N$

	/**
	 * Strips type from Name
	 */
	public String stripType(String key) {
		String[] split = key.split(":");
		if (split.length == 2) {
			return split[1];
		} else {
			return key;
		}
	}

	/**
	 * alias for {@link ModelProperties#stripType(String)}
	 */
	public String name(String key) {
		return stripType(key);
	}

	/**
	 * returns type from property name
	 */
	public String type(String key) {
		String[] split = key.split(":");
		if (split.length > 0) {
			return split[0];
		} else {
			return "";
		}
	}


	public String rgbToString(RGB rgb) {
		return String.format("#%02x%02x%02x", rgb.red, rgb.green, rgb.blue);
	}

	public RGB stringToRGB(String string) {
		System.out.println("String to rgb: " + string);
		if ("White".equals(string)) {
			return new RGB(0xff, 0xff, 0xff);
		} else if ("Light Yellow".equals(string)) {
			return new RGB(0xfa, 0xff, 0xa2);
		} else if ("Medium Yellow".equals(string)) {
			return new RGB(0xf4, 0xff, 0x4b);
		} else if ("Yellow".equals(string)) {
			return new RGB(0xed, 0xfc, 0x00);
		} else if ("Light Blue".equals(string)) {
			return new RGB(0xd4, 0xd5, 0xe9);
		} else if ("Medium Blue".equals(string)) {
			return new RGB(0x76, 0x78, 0xff);
		} else if ("Blue".equals(string)) {
			return new RGB(0x00, 0x02, 0xf8);
		} else if ("Light Green".equals(string)) {
			return new RGB(0xc8, 0xf8, 0xc9);
		} else if ("Medium Green".equals(string)) {
			return new RGB(0x7a, 0xfa, 0x7e);
		} else if ("Green".equals(string)) {
			return new RGB(0x1a, 0xf5, 0x20);
		} else if ("Light Red".equals(string)) {
			return new RGB(0xfd, 0xcc, 0xcc);
		} else if ("Medium Red".equals(string)) {
			return new RGB(0xf9, 0x59, 0x59);
		} else if ("Red".equals(string)) {
			return new RGB(0xf6, 0x20, 0x20);
		} else if (string != null && string.matches("\\#[a-fA-F0-9]{6}")) {
			System.out
					.println("BaseNodePropertyAdvisor.KSMColorCellEditor.stringToRGB()");
			String sr = string.substring(1, 3);
			String sg = string.substring(3, 5);
			String sb = string.substring(5, 7);
			int r = Integer.parseInt(sr, 16);
			int g = Integer.parseInt(sg, 16);
			int b = Integer.parseInt(sb, 16);
			return new RGB(r, g, b);
		} else {
			return new RGB(0, 0, 0);
		}
	}
}