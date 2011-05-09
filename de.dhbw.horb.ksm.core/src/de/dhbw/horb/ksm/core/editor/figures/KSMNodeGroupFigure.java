package de.dhbw.horb.ksm.core.editor.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class KSMNodeGroupFigure extends Panel {
	private Label label;

	public KSMNodeGroupFigure() {
		setLayoutManager(new XYLayout());
		setBorder(new LineBorder(ColorConstants.gray, 2, Graphics.LINE_DASHDOT));
		setBackgroundColor(ColorConstants.white);

		label = new Label();
		add(label);
	}

	public String getText() {
		return label.getText();
	}

	@Override
	public void setBounds(Rectangle rect) {
		super.setBounds(rect);
		label.setBounds(new Rectangle(0, 0, getBounds().width, label
				.getPreferredSize().height));
	}

	public void setCaption(String caption) {
		label.setText(caption);
		label.repaint();
	}

	@Override
	protected boolean useLocalCoordinates() {
		return true;
	}
}