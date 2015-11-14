package com.eclipse.johnfreier.compareit.popup.actions;

import javax.swing.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

class CompareItem implements IStreamContentAccessor, ITypedElement,
		IModificationDate {

	private String contents, name;

	CompareItem(String name, String contents) {
		this.name = name;
		this.contents = contents;
	}

	@Override
	public long getModificationDate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return ITypedElement.TEXT_TYPE;
	}

	@Override
	public InputStream getContents() throws CoreException {
		// TODO Auto-generated method stub
		return new ByteArrayInputStream(contents.getBytes());
	}

	@Override
	public org.eclipse.swt.graphics.Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}