package com.eclipse.johnfreier.compareit.popup.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

public class CompareItAction implements IEditorActionDelegate {

	private Shell shell;
	
	private String copied = new String();
	
	private static final String COPY_1 = "Compare text #1";
	private static final String COPY_2 = "Compare text #2";
	
	/**
	 * Constructor for Action1.
	 */
	public CompareItAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		
		// Get any selected text.
		IEditorPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	    if ( part instanceof ITextEditor ) {
	        final ITextEditor editor = (ITextEditor)part;
	        ISelection sel = editor.getSelectionProvider().getSelection();
	        
	        if ( sel instanceof TextSelection ) {
	        	
	            final TextSelection textSel = (TextSelection)sel;
	            String selectedCopy = textSel.getText();
	            
	            if (this.copied.isEmpty()) {
	            	action.setText(COPY_2);
	            	this.copied = selectedCopy;
	            	return;
	            }
	            
	            action.setText(COPY_1);
	            
	            CompareConfiguration configuration = new CompareConfiguration();
	            CompareUI.openCompareDialog(new CompareEditorInput(configuration) {
	            	
	                @Override
	                protected Object prepareInput(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
	                	
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
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	@Override
	public void setActiveEditor(IAction arg0, IEditorPart arg1) {
	}

}
