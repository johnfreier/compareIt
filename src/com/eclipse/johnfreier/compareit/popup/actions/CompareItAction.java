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
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class CompareItAction extends AbstractHandler {

	private String copied = new String();

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
				
				IEclipsePreferences preferences = InstanceScope.INSTANCE
			    		  .getNode("com.eclipse.johnfreier.compareit.copy");

				if (this.copied.isEmpty()) {
					preferences.putBoolean("copy", true);
					this.copied = selectedCopy;
					return null;
				}

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
						preferences.putBoolean("copy", false);

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
