package de.dhbw.horb.ksm.core.editor.parts.editmanager;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;


public class MyCellEditorLocator implements CellEditorLocator {
	private IFigure figure;

	public MyCellEditorLocator(IFigure figure) {
		this.figure = figure;
	}

	@Override
	public void relocate(CellEditor celleditor) {
		Text textEntry = (Text) celleditor.getControl();
		Point pref = textEntry.computeSize(SWT.DEFAULT, SWT.DEFAULT);

		Rectangle rect = figure.getBounds().getCopy();
		figure.translateToAbsolute(rect);
		textEntry.setBounds(rect.x - 1, rect.y - 1, pref.x + 1, pref.y + 1);
	}
}