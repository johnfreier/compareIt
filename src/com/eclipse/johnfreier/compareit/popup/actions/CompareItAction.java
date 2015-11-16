package com.eclipse.johnfreier.compareit.popup.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class CompareItAction extends AbstractHandler {

	private Shell shell;

	private String copied = new String();

	private static final String COPY_1 = "Compare text #1";
	private static final String COPY_2 = "Compare text #2";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// Get any selected text.
		IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActiveEditor();
		if (part instanceof ITextEditor) {
			final ITextEditor editor = (ITextEditor) part;
			ISelection sel = editor.getSelectionProvider().getSelection();

			if (sel instanceof TextSelection) {

				final TextSelection textSel = (TextSelection) sel;
				String selectedCopy = textSel.getText();

				if (this.copied.isEmpty()) {
					//event.getCommand().
					//action.setText(COPY_2);
					this.copied = selectedCopy;
					return null;
				}

				//action.setText(COPY_1);

				CompareConfiguration configuration = new CompareConfiguration();
				CompareUI.openCompareDialog(new CompareEditorInput(
						configuration) {

					@Override
					protected Object prepareInput(final IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {

						String text1 = copied;
						String text2 = selectedCopy;

						copied = "";

						CompareItem left = new CompareItem("1", text1);
						CompareItem right = new CompareItem("2", text2);

						DiffNode diffNode = new DiffNode(left, right);
						return diffNode;
					}

				});

			}
		}
		return null;
	}

}
