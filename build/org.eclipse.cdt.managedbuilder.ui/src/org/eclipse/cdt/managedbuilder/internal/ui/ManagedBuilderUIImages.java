package org.eclipse.cdt.managedbuilder.internal.ui;

/**********************************************************************
 * Copyright (c) 2002,2003 Rational Software Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v0.5
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v05.html
 * 
 * Contributors: 
 * IBM Rational Software - Initial API and implementation
 * **********************************************************************/

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.cdt.ui.*;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * Bundle of all images used by the C plugin.
 */
public class ManagedBuilderUIImages {
	
	// The plugin registry
	private static ImageRegistry imageRegistry = new ImageRegistry();

	// Subdirectory (under the package containing this class) where 16 color images are
	private static URL fgIconBaseURL;
	static {
		try {
			fgIconBaseURL= new URL(ManagedBuilderUIPlugin.getDefault().getDescriptor().getInstallURL(), "icons/" );
		} catch (MalformedURLException e) {
			CUIPlugin.getDefault().log(e);
		}
	}	
	private static final String NAME_PREFIX= ManagedBuilderUIPlugin.getUniqueIdentifier() + '.';
	private static final int NAME_PREFIX_LENGTH= NAME_PREFIX.length();
	private static final String T= "full/";

	public static final String T_BUILD= T + "build16/";



	// For the managed build images
	public static final String IMG_BUILD_CONFIG = NAME_PREFIX + "build_configs.gif";
	public static final ImageDescriptor DESC_BUILD_CONFIG = createManaged(T_BUILD, IMG_BUILD_CONFIG);
	public static final String IMG_BUILD_COMPILER = NAME_PREFIX + "config-compiler.gif";
	public static final ImageDescriptor DESC_BUILD_COMPILER = createManaged(T_BUILD, IMG_BUILD_COMPILER);
	public static final String IMG_BUILD_LINKER = NAME_PREFIX + "config-linker.gif";
	public static final ImageDescriptor DESC_BUILD_LINKER = createManaged(T_BUILD, IMG_BUILD_LINKER);
	public static final String IMG_BUILD_LIBRARIAN = NAME_PREFIX + "config-librarian.gif";
	public static final ImageDescriptor DESC_BUILD_LIBRARIAN = createManaged(T_BUILD, IMG_BUILD_LIBRARIAN);
	public static final String IMG_BUILD_COMMAND = NAME_PREFIX + "config-command.gif";
	public static final ImageDescriptor DESC_BUILD_COMMAND = createManaged(T_BUILD, IMG_BUILD_COMMAND);
	public static final String IMG_BUILD_PREPROCESSOR = NAME_PREFIX + "config-preprocessor.gif";
	public static final ImageDescriptor DESC_BUILD_PREPROCESSOR = createManaged(T_BUILD, IMG_BUILD_PREPROCESSOR);
	public static final String IMG_BUILD_TOOL = NAME_PREFIX + "config-tool.gif";
	public static final ImageDescriptor DESC_BUILD_TOOL = createManaged(T_BUILD, IMG_BUILD_TOOL);
	public static final String IMG_BUILD_CAT = NAME_PREFIX + "config-category.gif";
	public static final ImageDescriptor DESC_BUILD_CAT = createManaged(T_BUILD, IMG_BUILD_CAT);

	
	private static ImageDescriptor createManaged(String prefix, String name) {
		return createManaged(imageRegistry, prefix, name);
	}
	
	private static ImageDescriptor createManaged(ImageRegistry registry, String prefix, String name) {
		ImageDescriptor result= ImageDescriptor.createFromURL(makeIconFileURL(prefix, name.substring(NAME_PREFIX_LENGTH)));
		registry.put(name, result);
		return result;
	}
	
	public static Image get(String key) {
		return imageRegistry.get(key);
	}
	
	private static ImageDescriptor create(String prefix, String name) {
		return ImageDescriptor.createFromURL(makeIconFileURL(prefix, name));
	}
	
	private static URL makeIconFileURL(String prefix, String name) {
		StringBuffer buffer= new StringBuffer(prefix);
		buffer.append(name);
		try {
			return new URL(fgIconBaseURL, buffer.toString());
		} catch (MalformedURLException e) {
			CUIPlugin.getDefault().log(e);
			return null;
		}
	}
	
	/**
	 * Sets all available image descriptors for the given action.
	 */	
	public static void setImageDescriptors(IAction action, String type, String relPath) {
		relPath= relPath.substring(NAME_PREFIX_LENGTH);
		action.setDisabledImageDescriptor(create(T + "d" + type, relPath));
		action.setHoverImageDescriptor(create(T + "c" + type, relPath));
		action.setImageDescriptor(create(T + "e" + type, relPath));
	}
	
	/**
	 * Helper method to access the image registry from the JavaPlugin class.
	 */
	static ImageRegistry getImageRegistry() {
		return imageRegistry;
	}
}
