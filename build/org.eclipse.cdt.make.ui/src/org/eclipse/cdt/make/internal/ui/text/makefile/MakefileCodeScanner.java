/*******************************************************************************
 * Copyright (c) 2000, 2009 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     QNX Software Systems - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.make.internal.ui.text.makefile;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.make.internal.ui.text.ColorManager;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWhitespaceDetector;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

public class MakefileCodeScanner extends AbstractMakefileCodeScanner {

	private final static String[] keywords = { "define", "endef", "ifdef", "ifndef", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		"ifeq", "ifneq", "else", "endif", "include", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		"-include", "sinclude", "override", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		"export", "unexport", "vpath" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	};

	private final static String[] functions = { "subst", "patsubst", "strip", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		"findstring", "filter", "sort", "dir", "notdir", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		"suffix", "basename", "addsuffix", "addprefix", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		"join", "word", "words", "wordlist", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		"firstword", "wildcard", "error", "warning", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		"shell", "origin", "foreach", "call" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	};

	public static final String[] fTokenProperties = new String[] {
			ColorManager.MAKE_KEYWORD_COLOR,
			ColorManager.MAKE_FUNCTION_COLOR,
			ColorManager.MAKE_MACRO_REF_COLOR,
			ColorManager.MAKE_MACRO_DEF_COLOR,
			ColorManager.MAKE_DEFAULT_COLOR
	};

	/**
	 * Constructor for MakefileCodeScanner
	 */
	public MakefileCodeScanner() {
		super();
		initialize();
	}

	@Override
	protected List<IRule> createRules() {
		IToken keyword = getToken(ColorManager.MAKE_KEYWORD_COLOR);
		IToken function = getToken(ColorManager.MAKE_FUNCTION_COLOR);
		IToken macroRef = getToken(ColorManager.MAKE_MACRO_REF_COLOR);
		IToken macroDef = getToken(ColorManager.MAKE_MACRO_DEF_COLOR);
		IToken other = getToken(ColorManager.MAKE_DEFAULT_COLOR);

		List<IRule> rules = new ArrayList<IRule>();

		// Add generic whitespace rule.
		rules.add(new WhitespaceRule(new IWhitespaceDetector() {
			@Override
			public boolean isWhitespace(char character) {
				return Character.isWhitespace(character);
			}
		}, other));

		// Put before the the word rules
		MultiLineRule defineRule = new MultiLineRule("define", "endef", macroDef); //$NON-NLS-1$ //$NON-NLS-2$
		defineRule.setColumnConstraint(0);
		rules.add(defineRule);

		rules.add(new MacroDefinitionRule(macroDef, Token.UNDEFINED));

		// Add word rule for keywords, types, and constants.
		// We restrict the detection of the keywords to be the first column to be valid.
		WordRule keyWordRule = new WordRule(new IWordDetector() {
			@Override
			public boolean isWordPart(char c) {
				return Character.isLetterOrDigit(c) || c == '_';
			}
			@Override
			public boolean isWordStart(char c) {
				return Character.isLetterOrDigit(c) || c == '_' || c == '-';
			}}, other);
		for (int i = 0; i < keywords.length; i++) {
			keyWordRule.addWord(keywords[i], keyword);
		}
		keyWordRule.setColumnConstraint(0);
		rules.add(keyWordRule);

		WordRule functionRule = new WordRule(new IWordDetector() {
			@Override
			public boolean isWordPart(char c) {
				return Character.isLetterOrDigit(c) || c == '_';
			}
			@Override
			public boolean isWordStart(char c) {
				return Character.isLetterOrDigit(c) || c == '_';
			}}, other);
		for (int i = 0; i < functions.length; i++)
			functionRule.addWord(functions[i], function);
		rules.add(functionRule);

		rules.add(new MacroReferenceRule(macroRef, "$(", ")")); //$NON-NLS-1$ //$NON-NLS-2$
		rules.add(new MacroReferenceRule(macroRef, "${", "}")); //$NON-NLS-1$ //$NON-NLS-2$

		setDefaultReturnToken(other);

		return rules;
	}

	/*
	 * @see AbstractMakefileCodeScanner#getTokenProperties()
	 */
	@Override
	protected String[] getTokenProperties() {
		return fTokenProperties;
	}

}
