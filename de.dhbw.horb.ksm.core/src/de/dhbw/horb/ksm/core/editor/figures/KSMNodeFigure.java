package de.dhbw.horb.ksm.core.editor.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class KSMNodeFigure extends Figure {
	private RoundedRectangle rectangleFigure;
	private Label label;

	public KSMNodeFigure() {
		rectangleFigure = new RoundedRectangle();
		rectangleFigure.setCornerDimensions(new Dimension(25, 25));
		rectangleFigure
				.setForegroundColor(org.eclipse.draw2d.ColorConstants.red);
		rectangleFigure.setLineWidth(3);

		label = new Label();

		add(rectangleFigure);
		add(label);
	}

	public void setNodeColor(RGB color) {
		rectangleFigure.setForegroundColor(new Color(Display.getCurrent(),
				color));
	}

	public String getText() {
		return label.getText();
	}

	public Rectangle getTextBounds() {
		return label.getTextBounds();
	}

	@Override
	public void setBounds(Rectangle rect) {
		super.setBounds(rect);
		rectangleFigure.setBounds(rect);
		label.setBounds(rect);
	}

	public void setCaption(String name) {
		label.setText(name);
	}
}