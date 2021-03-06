/*******************************************************************************
 * Copyright (c) 2004, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Markus Schorn (Wind River Systems)
 *******************************************************************************/
package org.eclipse.cdt.internal.core.dom.parser.c;

import org.eclipse.cdt.core.dom.ILinkage;
import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.ILabel;
import org.eclipse.cdt.core.dom.ast.IScope;
import org.eclipse.cdt.internal.core.dom.Linkage;
import org.eclipse.core.runtime.PlatformObject;

/**
 * Represents a label.
 */
public class CLabel extends PlatformObject implements ILabel {
	private final IASTName labelStatement;

	public CLabel(IASTName statement) {
		labelStatement = statement;
		statement.setBinding(this);
	}

	public IASTNode getPhysicalNode() {
		return labelStatement;
	}

	@Override
	public IASTLabelStatement getLabelStatement() {
		return (IASTLabelStatement) labelStatement.getParent();
	}

	@Override
	public String getName() {
		return labelStatement.toString();
	}

	@Override
	public char[] getNameCharArray() {
		return labelStatement.toCharArray();
	}

	@Override
	public IScope getScope() {
		return CVisitor.getContainingScope(labelStatement.getParent());
	}

	@Override
	public ILinkage getLinkage() {
		return Linkage.C_LINKAGE;
	}

	@Override
	public IBinding getOwner() {
		return CVisitor.findEnclosingFunction(labelStatement);
	}

	@Override
	public String toString() {
		return getName();
	}
}
