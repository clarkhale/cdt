/*******************************************************************************
* Copyright (c) 2006, 2008 IBM Corporation and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     IBM Corporation - initial API and implementation
*********************************************************************************/

// This file was generated by LPG

package org.eclipse.cdt.internal.core.dom.lrparser.gpp;

public interface GPPParsersym {
    public final static int
      TK__Complex = 14,
      TK__Imaginary = 15,
      TK_asm = 5,
      TK_auto = 31,
      TK_bool = 16,
      TK_break = 82,
      TK_case = 83,
      TK_catch = 126,
      TK_char = 17,
      TK_class = 60,
      TK_const = 28,
      TK_const_cast = 47,
      TK_continue = 84,
      TK_default = 85,
      TK_delete = 74,
      TK_do = 86,
      TK_double = 18,
      TK_dynamic_cast = 48,
      TK_else = 129,
      TK_enum = 62,
      TK_explicit = 32,
      TK_export = 92,
      TK_extern = 33,
      TK_false = 49,
      TK_float = 19,
      TK_for = 87,
      TK_friend = 34,
      TK_goto = 88,
      TK_if = 89,
      TK_inline = 35,
      TK_int = 20,
      TK_long = 21,
      TK_mutable = 36,
      TK_namespace = 66,
      TK_new = 75,
      TK_operator = 9,
      TK_private = 110,
      TK_protected = 111,
      TK_public = 112,
      TK_register = 37,
      TK_reinterpret_cast = 50,
      TK_return = 90,
      TK_short = 22,
      TK_signed = 23,
      TK_sizeof = 51,
      TK_static = 38,
      TK_static_cast = 52,
      TK_struct = 63,
      TK_switch = 91,
      TK_template = 61,
      TK_this = 53,
      TK_throw = 67,
      TK_try = 78,
      TK_true = 54,
      TK_typedef = 39,
      TK_typeid = 55,
      TK_typename = 13,
      TK_union = 64,
      TK_unsigned = 24,
      TK_using = 68,
      TK_virtual = 30,
      TK_void = 25,
      TK_volatile = 29,
      TK_wchar_t = 26,
      TK_while = 81,
      TK_integer = 56,
      TK_floating = 57,
      TK_charconst = 58,
      TK_stringlit = 42,
      TK_identifier = 1,
      TK_Completion = 2,
      TK_EndOfCompletion = 11,
      TK_Invalid = 130,
      TK_LeftBracket = 70,
      TK_LeftParen = 3,
      TK_Dot = 127,
      TK_DotStar = 97,
      TK_Arrow = 113,
      TK_ArrowStar = 96,
      TK_PlusPlus = 44,
      TK_MinusMinus = 45,
      TK_And = 12,
      TK_Star = 10,
      TK_Plus = 40,
      TK_Minus = 41,
      TK_Tilde = 6,
      TK_Bang = 46,
      TK_Slash = 98,
      TK_Percent = 99,
      TK_RightShift = 93,
      TK_LeftShift = 94,
      TK_LT = 65,
      TK_GT = 77,
      TK_LE = 100,
      TK_GE = 101,
      TK_EQ = 102,
      TK_NE = 103,
      TK_Caret = 104,
      TK_Or = 105,
      TK_AndAnd = 106,
      TK_OrOr = 107,
      TK_Question = 114,
      TK_Colon = 73,
      TK_ColonColon = 4,
      TK_DotDotDot = 95,
      TK_Assign = 79,
      TK_StarAssign = 115,
      TK_SlashAssign = 116,
      TK_PercentAssign = 117,
      TK_PlusAssign = 118,
      TK_MinusAssign = 119,
      TK_RightShiftAssign = 120,
      TK_LeftShiftAssign = 121,
      TK_AndAssign = 122,
      TK_CaretAssign = 123,
      TK_OrAssign = 124,
      TK_Comma = 76,
      TK_RightBracket = 125,
      TK_RightParen = 71,
      TK_RightBrace = 80,
      TK_SemiColon = 43,
      TK_LeftBrace = 72,
      TK_typeof = 27,
      TK___alignof__ = 59,
      TK___attribute__ = 7,
      TK___declspec = 8,
      TK_MAX = 108,
      TK_MIN = 109,
      TK_ERROR_TOKEN = 69,
      TK_EOF_TOKEN = 128;

      public final static String orderedTerminalSymbols[] = {
                 "",
                 "identifier",
                 "Completion",
                 "LeftParen",
                 "ColonColon",
                 "asm",
                 "Tilde",
                 "__attribute__",
                 "__declspec",
                 "operator",
                 "Star",
                 "EndOfCompletion",
                 "And",
                 "typename",
                 "_Complex",
                 "_Imaginary",
                 "bool",
                 "char",
                 "double",
                 "float",
                 "int",
                 "long",
                 "short",
                 "signed",
                 "unsigned",
                 "void",
                 "wchar_t",
                 "typeof",
                 "const",
                 "volatile",
                 "virtual",
                 "auto",
                 "explicit",
                 "extern",
                 "friend",
                 "inline",
                 "mutable",
                 "register",
                 "static",
                 "typedef",
                 "Plus",
                 "Minus",
                 "stringlit",
                 "SemiColon",
                 "PlusPlus",
                 "MinusMinus",
                 "Bang",
                 "const_cast",
                 "dynamic_cast",
                 "false",
                 "reinterpret_cast",
                 "sizeof",
                 "static_cast",
                 "this",
                 "true",
                 "typeid",
                 "integer",
                 "floating",
                 "charconst",
                 "__alignof__",
                 "class",
                 "template",
                 "enum",
                 "struct",
                 "union",
                 "LT",
                 "namespace",
                 "throw",
                 "using",
                 "ERROR_TOKEN",
                 "LeftBracket",
                 "RightParen",
                 "LeftBrace",
                 "Colon",
                 "delete",
                 "new",
                 "Comma",
                 "GT",
                 "try",
                 "Assign",
                 "RightBrace",
                 "while",
                 "break",
                 "case",
                 "continue",
                 "default",
                 "do",
                 "for",
                 "goto",
                 "if",
                 "return",
                 "switch",
                 "export",
                 "RightShift",
                 "LeftShift",
                 "DotDotDot",
                 "ArrowStar",
                 "DotStar",
                 "Slash",
                 "Percent",
                 "LE",
                 "GE",
                 "EQ",
                 "NE",
                 "Caret",
                 "Or",
                 "AndAnd",
                 "OrOr",
                 "MAX",
                 "MIN",
                 "private",
                 "protected",
                 "public",
                 "Arrow",
                 "Question",
                 "StarAssign",
                 "SlashAssign",
                 "PercentAssign",
                 "PlusAssign",
                 "MinusAssign",
                 "RightShiftAssign",
                 "LeftShiftAssign",
                 "AndAssign",
                 "CaretAssign",
                 "OrAssign",
                 "RightBracket",
                 "catch",
                 "Dot",
                 "EOF_TOKEN",
                 "else",
                 "Invalid"
             };

    public final static boolean isValidForParser = true;
}
