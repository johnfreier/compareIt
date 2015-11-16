package com.eclipse.johnfreier.compareit.popup.actions;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

public class CompareItCompoundContributionItem extends CompoundContributionItem {
	  
	  private static final String COPY_1 = "Compare text #1";
	  private static final String COPY_2 = "Compare text #2";

	  @Override
	  protected IContributionItem[] getContributionItems() {
	    final CommandContributionItemParameter contributionParameter = 
	      new CommandContributionItemParameter(
	        PlatformUI.getWorkbench().getActiveWorkbenchWindow(),
	        "com.eclipse.johnfreier.compareit.popup.actions.menuitem", "com.eclipse.johnfreier.compareit.popup.actions.myCommand",
	        SWT.NONE);
	    
	    IEclipsePreferences preferences = InstanceScope.INSTANCE
	    		  .getNode("com.eclipse.johnfreier.compareit.copy");
	    Boolean copy = preferences.getBoolean("copy", false);
	    
	    contributionParameter.label = COPY_1;
	    
	    if (copy) {
	    	contributionParameter.label = COPY_2;
	    }
	    
	    return new IContributionItem[] { 
	    new CommandContributionItem(contributionParameter) };
	  }
	}