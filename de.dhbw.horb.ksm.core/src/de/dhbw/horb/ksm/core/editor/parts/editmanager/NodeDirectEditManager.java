package de.dhbw.horb.ksm.core.editor.parts.editmanager;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Text;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeEditPart;
import de.dhbw.horb.ksm.model.api.Node;
import de.dhbw.horb.ksm.model.api.Properties;

public class NodeDirectEditManager extends DirectEditManager {
	private Font scaledFont;
	private final Properties properties;

	public NodeDirectEditManager(KSMNodeEditPart nodeGroupEditPart) {
		super(nodeGroupEditPart, TextCellEditor.class, new MyCellEditorLocator(
				nodeGroupEditPart.getFigure()));
		Node model = nodeGroupEditPart.getModel();
		properties = model.getProperties();
	}

	@Override
	protected void bringDown() {
		Font disposeFont = this.scaledFont;
		this.scaledFont = null;
		super.bringDown();
		if (disposeFont != null) {
			disposeFont.dispose();
		}
	}

	@Override
	protected void unhookListeners() {
		super.unhookListeners();
	}

	@Override
	protected void initCellEditor() {
		Text text = (Text) getCellEditor().getControl();
		String caption = properties.getString(ModelProperties.INSTANCE
				.stripType(ModelProperties.INSTANCE.NODE_VISUAL_CAPTION));
		if (caption == null) {
			caption = "-";
		}
		getCellEditor().setValue(caption);
		IFigure figure = ((GraphicalEditPart) getEditPart()).getFigure();
		scaledFont = figure.getFont();
		FontData data = scaledFont.getFontData()[0];
		Dimension fontSize = new Dimension(0, data.getHeight());
		data.setHeight(fontSize.height);
		scaledFont = new Font(null, data);

		text.setFont(scaledFont);
		text.selectAll();
	}
}