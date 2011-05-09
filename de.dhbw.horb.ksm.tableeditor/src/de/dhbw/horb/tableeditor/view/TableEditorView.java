package de.dhbw.horb.tableeditor.view;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.part.PageSite;

import de.dhbw.horb.ksm.core.Activator;
import de.dhbw.horb.ksm.core.editor.commands.NodeRenameCommand;
import de.dhbw.horb.ksm.core.editor.model.ModelProperties;
import de.dhbw.horb.ksm.core.editor.parts.KSMNodeEditPart;
import de.dhbw.horb.ksm.core.editor.ui.DiagramEditor;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.api.Node;

public class TableEditorView extends PageBookView {
	public static final String VIEW_ID = "de.dhbw.horb.ksm.tableditor.view"; //$NON-NLS-1$
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
			TableEditorPage page = new TableEditorPage(editor);
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

	public class TableEditorPage extends Page implements CommandStackListener {
		private final DiagramEditor editor;
		private TableViewer tableViewer;
		private final String columnCaptionCaption = "Caption";
		private final String columnCaptionX = "X";

		public TableEditorPage(DiagramEditor editor) {
			this.editor = editor;
		}

		@Override
		public void createControl(final Composite parent) {
			// Create a Table Viewer with Single-Selection Support
			tableViewer = new TableViewer(parent, SWT.SINGLE);
			tableViewer.getTable().setLinesVisible(true);
			tableViewer.getTable().setHeaderVisible(true);

			TableViewerColumn tableViewerColumnCaption = new TableViewerColumn(
					tableViewer, SWT.NONE);
			tableViewerColumnCaption
					.setLabelProvider(new PropertyLabelProvider(
							ModelProperties.INSTANCE.NODE_VISUAL_CAPTION));
			tableViewerColumnCaption
					.setEditingSupport(new StringPropertyEditingSupport(editor,
							tableViewer.getTable(), tableViewerColumnCaption
									.getViewer(),
							ModelProperties.INSTANCE.NODE_VISUAL_CAPTION));

			TableColumn columnCaption = tableViewerColumnCaption.getColumn();
			columnCaption.setText(columnCaptionCaption);
			columnCaption.setWidth(100);
			columnCaption.setResizable(true);
			columnCaption.setMoveable(true);

			TableViewerColumn tableViewerColumnX = new TableViewerColumn(
					tableViewer, SWT.NONE);
			tableViewerColumnX.setLabelProvider(new PropertyLabelProvider(
					ModelProperties.INSTANCE.NODE_VISUAL_LOCATION_X));

			TableColumn columnX = tableViewerColumnX.getColumn();
			columnX.setText(columnCaptionX);
			columnX.setWidth(100);
			columnX.setResizable(true);
			columnX.setMoveable(true);

			tableViewer.setContentProvider(new ArrayContentProvider());

			((CommandStack) editor.getAdapter(CommandStack.class))
					.addCommandStackListener(this);
			refreshTable();

			editor.getEditorSite()
					.getSelectionProvider()
					.addSelectionChangedListener(
							new ISelectionChangedListener() {

								@Override
								public void selectionChanged(
										SelectionChangedEvent event) {
									ISelection selection = event.getSelection();
									if (selection instanceof IStructuredSelection) {
										IStructuredSelection s = (IStructuredSelection) selection;
										System.out.println(s.getFirstElement());
										if (s.getFirstElement() instanceof KSMNodeEditPart) {
											KSMNodeEditPart nodeEditPart = (KSMNodeEditPart) s
													.getFirstElement();
											System.out.println(nodeEditPart
													.getModel());
											tableViewer.setSelection(
													new StructuredSelection(
															nodeEditPart
																	.getModel()),
													true);
										}
									}
								}
							});

		}

		protected void refreshTable() {
			KSM model = editor.getModel();
			List<Node> list = new ArrayList<Node>();
			for (Node node : model.getAllNodes()) {
				list.add(node); // XXX change getAllNodes to return a list
			}
			tableViewer.setInput(list.toArray());
		}

		@Override
		public Control getControl() {
			return tableViewer.getControl();
		}

		@Override
		public void setFocus() {
			tableViewer.getTable().setFocus();
		}

		@Override
		public void commandStackChanged(EventObject event) {
			refreshTable();
		}

		@Override
		public void dispose() {
			((CommandStack) editor.getAdapter(CommandStack.class))
					.removeCommandStackListener(this);
			super.dispose();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public class StringPropertyEditingSupport extends EditingSupport {
		private final String stringPropertyName;
		private final Table table;
		private final DiagramEditor editor;

		public StringPropertyEditingSupport(DiagramEditor editor,
				final Table table, final ColumnViewer columnViewer,
				final String stringPropertyName) {
			super(columnViewer);
			this.editor = editor;
			this.table = table;
			this.stringPropertyName = ModelProperties.INSTANCE
					.stripType(stringPropertyName);
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (element instanceof Node && value instanceof String) {
				CommandStack commandStack = (CommandStack) editor
						.getAdapter(CommandStack.class);
				GraphicalViewer graphicalViewer = (GraphicalViewer) editor
						.getAdapter(GraphicalViewer.class);

				Object object = graphicalViewer.getEditPartRegistry().get(
						element);
				if (object instanceof KSMNodeEditPart) {
					KSMNodeEditPart ep = (KSMNodeEditPart) object;
					NodeRenameCommand nodeRenameCommand = new NodeRenameCommand(
							ep);
					nodeRenameCommand.setNewCaption((String) value);
					commandStack.execute(nodeRenameCommand);
				} else {
					Platform.getLog(Platform.getBundle(Activator.PLUGIN_ID))
							.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
									"Cant get EditPart for "
											+ element.toString()));
				}
			}
		}

		@Override
		protected Object getValue(Object element) {
			if (element instanceof Node) {
				return ((Node) element).getProperties().getString(
						stringPropertyName);
			} else {
				return null;
			}
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			if (element instanceof Node) {
				String value = (String) getValue(element);
				TextCellEditor textCellEditor = new TextCellEditor(table);
				textCellEditor.setValue(value);
				return textCellEditor;
			} else {
				return null;
			}
		}

		@Override
		protected boolean canEdit(Object element) {
			return true;
		}
	}
}
