package de.dhbw.horb.simulator.view;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EventObject;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.part.PageSite;
import org.swtchart.Chart;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries.SeriesType;

import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.ui.DiagramEditor;
import de.dhbw.horb.ksm.model.api.Node;

public class SimulatorView extends PageBookView {
	public final static String VIEW_ID = "ksm.simulator.views.SimulatorView";

	private final String message = "No compatible Editor selected";

	@Override
	public void setFocus() {

	}

	@Override
	protected IPage createDefaultPage(PageBook book) {
		MessagePage page = new MessagePage();
		initPage(page);
		page.createControl(book);
		page.setMessage(message);
		return page;
	}

	@Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
		if (part instanceof DiagramEditor) {
			DiagramEditor editor = (DiagramEditor) part;
			SimulatorPage page = new SimulatorPage(editor);
			page.init(new PageSite(getViewSite()));
			page.createControl(getPageBook());
			return new PageRec(part, page);
		}
		return null;
	}

	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		pageRecord.page.dispose();
		pageRecord.dispose();
	}

	@Override
	protected IWorkbenchPart getBootstrapPart() {
		IWorkbenchPage page = getSite().getPage();
		if (page != null) {
			return page.getActiveEditor();
		}
		return null;
	}

	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		return (part instanceof DiagramEditor);
	}

	private class SimulatorPage extends Page implements CommandStackListener {
		private Chart chart;
		private ILineSeries xPositionSeries;
		private final DiagramEditor editor;
		private ILineSeries yPositionSeries;

		public SimulatorPage(DiagramEditor editor) {
			this.editor = editor;
		}

		@Override
		public void createControl(Composite parent) {
			chart = new Chart(parent, SWT.NONE);

			chart.getTitle().setVisible(false);
			chart.getAxisSet().getXAxis(0).getTitle().setText("KSM Knoten");
			chart.getAxisSet().getYAxis(0).getTitle().setText("Position");

			xPositionSeries = (ILineSeries) chart.getSeriesSet().createSeries(
					SeriesType.LINE, "X-Position");
			xPositionSeries.setLineColor(Display.getCurrent().getSystemColor(
					SWT.COLOR_BLUE));
			yPositionSeries = (ILineSeries) chart.getSeriesSet().createSeries(
					SeriesType.LINE, "Y-Position");
			yPositionSeries.setLineColor(Display.getCurrent().getSystemColor(
					SWT.COLOR_RED));

			// Enable Strings as X-Axis Ticks
			chart.getAxisSet().getXAxes()[0].enableCategory(true);

			refresh();

			((CommandStack) editor.getAdapter(CommandStack.class))
					.addCommandStackListener(this);
		}

		@Override
		public void commandStackChanged(EventObject event) {
			refresh();
		}

		private void refresh() {
			ArrayList<int[]> listXY = new ArrayList<int[]>();
			ArrayList<String> listCaptions = new ArrayList<String>();
			for (Node n : editor.getModel().getAllNodes()) {
				BigInteger locationX = n
						.getProperties()
						.getInteger(
								ModelProperties.INSTANCE
										.stripType(ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_X));
				BigInteger locationY = n
						.getProperties()
						.getInteger(
								ModelProperties.INSTANCE
										.stripType(ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_Y));
				listXY.add(new int[] { locationX.intValue(),
						locationY.intValue() });

				String caption = n
						.getProperties()
						.getString(
								ModelProperties.INSTANCE
										.stripType(ModelProperties.INSTANCE.NODE_VISUAL_CAPTION));
				listCaptions.add(caption);
			}
			double[] seriesX = new double[listXY.size()];
			double[] seriesY = new double[listXY.size()];
			String[] xCaption = new String[listXY.size()];
			for (int i = 0; i < listXY.size(); i++) {
				seriesX[i] = listXY.get(i)[0];
				seriesY[i] = listXY.get(i)[1];
				xCaption[i] = listCaptions.get(i);
			}

			// Set Y-Axis Data
			xPositionSeries.setYSeries(seriesX);
			yPositionSeries.setYSeries(seriesY);

			// Set X-Axis Description
			chart.getAxisSet().getXAxes()[0].setCategorySeries(xCaption);

			// adjust the axis range and redraw
			chart.getAxisSet().adjustRange();
			chart.redraw();
		}

		@Override
		public Control getControl() {
			return chart;
		}

		@Override
		public void setFocus() {
			chart.setFocus();
		}

		@Override
		public void dispose() {
			((CommandStack) editor.getAdapter(CommandStack.class))
					.removeCommandStackListener(this);
			super.dispose();
			chart.dispose();
		}
	}
}
