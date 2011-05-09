package de.dhbw.horb.ksm.core.editor.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import de.dhbw.horb.ksm.core.Activator;
import de.dhbw.horb.ksm.core.KSMMessages;
import de.dhbw.horb.ksm.core.editor.dnd.DiagramTemplateTransferDropTargetListener;
import de.dhbw.horb.ksm.core.editor.model.NodeGroupRequestFactory;
import de.dhbw.horb.ksm.core.editor.model.NodeRequestFactory;
import de.dhbw.horb.ksm.core.editor.parts.PartFactory;
import de.dhbw.horb.ksm.core.editor.parts.outline.TreePartFactory;
import de.dhbw.horb.ksm.model.api.KSM;
import de.dhbw.horb.ksm.model.impl.KSMFactory;

public class DiagramEditor extends GraphicalEditorWithPalette {
	public static final String EDITOR_ID = "ksm.core.editor";

	private PaletteRoot paletteRoot;
	private KeyHandler sharedKeyHandler;

	private boolean savePreviouslyNeeded;

	private KSM ksmModel = KSMFactory.createEmptyKSM();

	public DiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	public KSM getModel() {
		return ksmModel;
	}

	protected KeyHandler getCommonKeyHandler() {
		if (sharedKeyHandler == null) {
			sharedKeyHandler = new KeyHandler();
			sharedKeyHandler
					.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
							getActionRegistry().getAction(
									ActionFactory.DELETE.getId()));
		}
		return sharedKeyHandler;
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		getGraphicalViewer().setRootEditPart(new ScalableRootEditPart());

		// set the factory to use for creating EditParts for elements in the
		// model
		getGraphicalViewer().setEditPartFactory(new PartFactory());
		getGraphicalViewer().setKeyHandler(
				new GraphicalViewerKeyHandler(getGraphicalViewer())
						.setParent(getCommonKeyHandler()));

	}

	@Override
	public void commandStackChanged(EventObject event) {
		if (isDirty()) {
			if (!this.savePreviouslyNeeded) {
				this.savePreviouslyNeeded = true;
				firePropertyChange(IEditorPart.PROP_DIRTY);
				firePropertyChange(ISaveablePart.PROP_DIRTY);
			}
		} else {
			savePreviouslyNeeded = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);
			firePropertyChange(ISaveablePart.PROP_DIRTY);
		}
		super.commandStackChanged(event);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if (type == IContentOutlinePage.class) {
			return new KSMContentOutlinePage();
		} else if (type == ZoomManager.class) {
			return ((ScalableRootEditPart) getGraphicalViewer()
					.getRootEditPart()).getZoomManager();

		}
		return super.getAdapter(type);
	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		setPartName(input.getName());
		IFile file = ((IFileEditorInput) input).getFile();
		try { // attempt to read from a file
			InputStream istream = file.getContents(false);
			ksmModel = KSMFactory.loadKSM(istream);
		} catch (Exception e) { // but if there's an error, create a new diagram
			e.printStackTrace();
			ksmModel = KSMFactory.createEmptyKSM();
		}
	}

	@Override
	protected void initializeGraphicalViewer() {
		// this uses the PartFactory set in configureGraphicalViewer
		// to create an EditPart for the diagram and sets it as the
		// content for the viewer
		getGraphicalViewer().setContents(this.ksmModel);
		getGraphicalViewer().addDropTargetListener(
				new DiagramTemplateTransferDropTargetListener(
						getGraphicalViewer()));
	}

	@Override
	protected void initializePaletteViewer() {
		super.initializePaletteViewer();
		getPaletteViewer().addDragSourceListener(
				new TemplateTransferDragSourceListener(getPaletteViewer()));
	}

	/**
	 * Save Editor content to File.
	 */
	@Override
	public void doSave(final IProgressMonitor monitor) {
		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void run() throws Exception {
				// TODO remove unecessary copy action
				IFile file = ((IFileEditorInput) getEditorInput()).getFile();
				ByteArrayOutputStream os = new ByteArrayOutputStream(512);
				KSMFactory.saveKSM(ksmModel, os);

				ByteArrayInputStream is = new ByteArrayInputStream(os
						.toByteArray());
				file.setContents(is, true, false, monitor);
				getCommandStack().markSaveLocation();
			}

			@Override
			public void handleException(Throwable exception) {
				exception.printStackTrace();
			}
		});
	}

	@Override
	public void doSaveAs() {
		final SaveAsDialog dialog = new SaveAsDialog(getSite()
				.getWorkbenchWindow().getShell());
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();
		final IPath path = dialog.getResult();

		if (path == null)
			return;

		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IFile file = workspace.getRoot().getFile(path);

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			public void execute(final IProgressMonitor monitor)
					throws CoreException {
				try {
					ByteArrayOutputStream os = new ByteArrayOutputStream(512);
					KSMFactory.saveKSM(ksmModel, os);

					ByteArrayInputStream is = new ByteArrayInputStream(
							os.toByteArray());
					file.create(is, true, monitor);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		try {
			new ProgressMonitorDialog(getSite().getWorkbenchWindow().getShell())
					.run(false, true, op);
			setInput(new FileEditorInput((IFile) file));
			getCommandStack().markSaveLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isDirty() {
		// rely on the command stack to determine dirty flag
		return getCommandStack().isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		// allow Save As
		return true;
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		if (this.paletteRoot == null) {
			ToolEntry tool;
			paletteRoot = new PaletteRoot();

			PaletteGroup controlGroup = new PaletteGroup("Control Group"); //$NON-NLS-1$

			tool = new SelectionToolEntry();
			controlGroup.add(tool);
			paletteRoot.setDefaultEntry(tool);

			tool = new ConnectionCreationToolEntry(
					KSMMessages.PaletteFactory_Connection,
					KSMMessages.PaletteFactory_CreateConnection,
					null,
					Activator
							.getImageDescriptor("icons/16/ksm-connection-new.png"), //$NON-NLS-1$
					Activator
							.getImageDescriptor("icons/32/ksm-connection-new.png")); //$NON-NLS-1$
			controlGroup.add(tool);

			paletteRoot.add(controlGroup);

			PaletteDrawer drawer = new PaletteDrawer(
					KSMMessages.PaletteFactory_Components, null);

			tool = new CombinedTemplateCreationEntry(
					KSMMessages.PaletteFactory_Node,
					KSMMessages.PaletteFactory_CreateNode,
					new NodeRequestFactory(this),
					Activator.getImageDescriptor("icons/16/ksm-node-new.png"), //$NON-NLS-1$
					Activator.getImageDescriptor("icons/32/ksm-node-new.png")); //$NON-NLS-1$
			drawer.add(tool);

			tool = new CombinedTemplateCreationEntry("Node-Group",
					"Create Node Group", new NodeGroupRequestFactory(this),
					Activator.getImageDescriptor("icons/16/ksm-nodegroup-new.png"), //$NON-NLS-1$
					Activator.getImageDescriptor("icons/32/ksm-nodegroup-new.png")); //$NON-NLS-1$
			drawer.add(tool);

			paletteRoot.add(drawer);
		}

		return paletteRoot;
	}

	// OutlineView Content
	private class KSMContentOutlinePage extends ContentOutlinePage {

		public KSMContentOutlinePage() {
			super(new TreeViewer());
		}

		public void init(IPageSite pageSite) {
			super.init(pageSite);
			ActionRegistry registry = DiagramEditor.this.getActionRegistry();
			IActionBars bars = pageSite.getActionBars();
			String id = ActionFactory.UNDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.REDO.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.DELETE.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));
			id = ActionFactory.PRINT.getId();
			bars.setGlobalActionHandler(id, registry.getAction(id));

			bars.updateActionBars();
		}

		@Override
		public void createControl(Composite parent) {
			super.createControl(parent);
			getViewer().setKeyHandler(DiagramEditor.this.getCommonKeyHandler());
			getViewer().setEditDomain(DiagramEditor.this.getEditDomain());
			getViewer().setEditPartFactory(new TreePartFactory());
			getViewer().setContents(DiagramEditor.this.getModel());

			DiagramEditor.this.getSelectionSynchronizer()
					.addViewer(getViewer());
		}

		public void dispose() {
			DiagramEditor.this.getSelectionSynchronizer().removeViewer(
					getViewer());
			super.dispose();
		}
	}
}