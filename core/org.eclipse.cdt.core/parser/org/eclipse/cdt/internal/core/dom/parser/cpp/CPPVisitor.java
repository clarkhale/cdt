/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/*
 * Created on Nov 29, 2004
 */
package org.eclipse.cdt.internal.core.dom.parser.cpp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.IASTArraySubscriptExpression;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTConditionalExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionList;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFieldReference;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTGotoStatement;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTInitializerExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPointer;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.IASTTypeIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;
import org.eclipse.cdt.core.dom.ast.IBasicType;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.IFunction;
import org.eclipse.cdt.core.dom.ast.IFunctionType;
import org.eclipse.cdt.core.dom.ast.IScope;
import org.eclipse.cdt.core.dom.ast.IType;
import org.eclipse.cdt.core.dom.ast.IVariable;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.c.ICFunctionScope;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCatchHandler;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTConstructorChainInitializer;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTConstructorInitializer;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTDeleteExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFieldReference;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionTryBlockDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTLinkageSpecification;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceAlias;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNewExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTQualifiedName;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTSimpleTypeConstructorExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateParameter;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateSpecialization;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTryBlockStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTypenameExpression;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDirective;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPBasicType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassScope;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPNamespace;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPScope;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;
import org.eclipse.cdt.core.dom.ast.gnu.IGNUASTCompoundStatementExpression;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.IGPPASTSimpleDeclSpecifier;

/**
 * @author aniefer
 */
public class CPPVisitor {

	/**
	 * @param name
	 */
	public static IBinding createBinding(IASTName name) {
		IASTNode parent = name.getParent();
		if( parent instanceof IASTNamedTypeSpecifier  ||
		    parent instanceof ICPPASTQualifiedName    ||
			parent instanceof ICPPASTBaseSpecifier ) 
		{
			return CPPSemantics.resolveBinding( name );
		} else if( parent instanceof IASTIdExpression ){
			return resolveBinding( parent );
		} else if( parent instanceof ICPPASTFieldReference ){
			return resolveBinding( parent );
		} else if( parent instanceof ICPPASTCompositeTypeSpecifier ){
			return createBinding( (ICPPASTCompositeTypeSpecifier) parent );
		} else if( parent instanceof IASTDeclarator ){
			return createBinding( (IASTDeclarator) parent );
		} else if( parent instanceof ICPPASTElaboratedTypeSpecifier ){
			return createBinding( (ICPPASTElaboratedTypeSpecifier) parent );
		} else if( parent instanceof IASTDeclaration )
			return createBinding( (IASTDeclaration) parent );
		return null;
	}
	
	private static IBinding createBinding( ICPPASTElaboratedTypeSpecifier elabType ){
	    IASTNode parent = elabType.getParent();
	    if( parent instanceof IASTSimpleDeclaration ){
	        IASTDeclarator [] dtors = ((IASTSimpleDeclaration)parent).getDeclarators();
	        if( dtors.length > 0 ){
	            IBinding binding = CPPSemantics.resolveBinding( elabType.getName() );
	            if( binding != null )
	                return binding;
	        }
	    }
	    
		ICPPScope scope = (ICPPScope) getContainingScope( elabType );
		CPPClassType binding = (CPPClassType) scope.getBinding( elabType.getName() );
		if( binding == null ){
			if( elabType.getKind() != IASTElaboratedTypeSpecifier.k_enum )
				binding = new CPPClassType( elabType );
			scope.addBinding( binding );
		} else {
			binding.addDeclaration( elabType );
		}
		return binding;
	}
	private static IBinding createBinding( ICPPASTCompositeTypeSpecifier compType ){
		ICPPScope scope = (ICPPScope) getContainingScope( compType );
		IBinding binding = scope.getBinding( compType.getName() );
		if( binding == null || !(binding instanceof ICPPClassType) ){
			binding = new CPPClassType( compType );
			scope.addBinding( binding );
		} else {
			((CPPClassType)binding).addDefinition( compType );
		}
		return binding;
	}
	private static IBinding createBinding( IASTDeclaration declaration ){
		if( declaration instanceof ICPPASTNamespaceDefinition ){
			ICPPASTNamespaceDefinition namespaceDef = (ICPPASTNamespaceDefinition) declaration;
			ICPPScope scope = (ICPPScope) getContainingScope( namespaceDef );
			CPPNamespace binding = (CPPNamespace) scope.getBinding( namespaceDef.getName() );
			if( binding == null )
				binding = new CPPNamespace( namespaceDef );
			else
				binding.addDefinition( namespaceDef );
			return binding;
		} else if( declaration instanceof ICPPASTUsingDirective ){
			return CPPSemantics.resolveBinding( ((ICPPASTUsingDirective) declaration).getQualifiedName() );
		}
		
			
		return null;
	}
	private static IBinding createBinding( IASTDeclarator declarator ){
		
		IASTNode parent = declarator.getParent();
		ICPPScope scope = (ICPPScope) getContainingScope( parent );
		IBinding binding = ( scope != null ) ? scope.getBinding( declarator.getName() ) : null;

		if( declarator instanceof ICPPASTFunctionDeclarator ){
			if( binding != null && binding instanceof IFunction ){
			    IFunction function = (IFunction) binding;
			    IFunctionType ftype = function.getType();
			    IType type = createType( (ICPPASTFunctionDeclarator) declarator );
			    if( ftype.equals( type ) ){
			        if( parent instanceof IASTSimpleDeclaration )
			            ((CPPFunction)function).addDeclaration( (ICPPASTFunctionDeclarator) declarator );
			        else 
			            ((CPPFunction)function).addDefinition( (ICPPASTFunctionDeclarator) declarator );
			        
			        return function;
			    }
			} 
			if( scope instanceof ICPPClassScope )
				binding = new CPPMethod( (ICPPASTFunctionDeclarator) declarator );
			else
				binding = new CPPFunction( (ICPPASTFunctionDeclarator) declarator );
		} else {
			if( parent instanceof IASTSimpleDeclaration ){
				IASTSimpleDeclaration simpleDecl = (IASTSimpleDeclaration) parent;
				if( simpleDecl.getDeclSpecifier().getStorageClass() == IASTDeclSpecifier.sc_typedef ){
					binding = new CPPTypedef( declarator );
				} else if( simpleDecl.getParent() instanceof ICPPASTCompositeTypeSpecifier ){
					binding = new CPPField( declarator );
				} else {
					binding = new CPPVariable( declarator );
				}
			} else if( parent instanceof IASTParameterDeclaration ){
				IASTParameterDeclaration param = (IASTParameterDeclaration) parent;
				IASTFunctionDeclarator fDtor = (IASTFunctionDeclarator) param.getParent();
				IFunction function = (IFunction) fDtor.getName().resolveBinding();
				binding = ((CPPFunction) function).resolveParameter( param );
			} else if( parent instanceof IASTFunctionDefinition ){
				
			}
		}
		if( scope != null )
		    scope.addBinding( binding );
		return binding;
	}

	public static IScope getContainingScope( IASTNode node ){
		if( node == null )
			return null;
		if( node instanceof IASTName )
			return getContainingScope( (IASTName) node );
		else if( node instanceof IASTDeclaration )
	        return getContainingScope( (IASTDeclaration) node );
	    else if( node instanceof IASTStatement )
	        return getContainingScope( (IASTStatement) node );
	    else if( node instanceof IASTDeclSpecifier )
	        return getContainingScope( (IASTDeclSpecifier) node );
	    else if( node instanceof IASTParameterDeclaration )
	        return getContainingScope( (IASTParameterDeclaration) node );
	    else if( node instanceof IASTEnumerator ){
	        //put the enumerators in the same scope as the enumeration
	        return getContainingScope( (IASTEnumerationSpecifier) node.getParent() );
	    }
	    
	    return getContainingScope( node.getParent() );
	}
	
	public static IScope getContainingScope( IASTName name ){
		IASTNode parent = name.getParent();
		if( parent instanceof ICPPASTQualifiedName ){
			IASTName [] names = ((ICPPASTQualifiedName) parent).getNames();
			int i = 0;
			for( ; i < names.length; i++ ){
				if( names[i] == name ) break;
			}
			if( i > 0 ){
				IBinding binding = names[i - 1].resolveBinding();
				if( binding instanceof ICPPClassType ){
					return ((ICPPClassType)binding).getCompositeScope();
				} else if( binding instanceof ICPPNamespace ){
					return ((ICPPNamespace)binding).getNamespaceScope();
				}
			}
		} else if( parent instanceof ICPPASTFieldReference ){
			IASTExpression owner = ((ICPPASTFieldReference)parent).getFieldOwner();
			IType type = CPPSemantics.getUltimateType( getExpressionType( owner ) );
			if( type instanceof ICPPClassType ){
				return ((ICPPClassType) type).getCompositeScope();
			}
		}
		return getContainingScope( parent );
	}
	/**
	 * @param declaration
	 * @return
	 */
	public static IScope getContainingScope(IASTDeclaration declaration) {
		IASTNode parent = declaration.getParent();
		if( parent instanceof IASTTranslationUnit ){
			return ((IASTTranslationUnit)parent).getScope();
		} else if( parent instanceof IASTDeclarationStatement ){
			return getContainingScope( (IASTStatement) parent );
		} else if( parent instanceof IASTForStatement ){
		    return ((IASTForStatement)parent).getScope();
		} else if( parent instanceof IASTCompositeTypeSpecifier ){
		    return ((IASTCompositeTypeSpecifier)parent).getScope();
		} else if( parent instanceof ICPPASTNamespaceDefinition ) {
			return ((ICPPASTNamespaceDefinition)parent).getScope();
		}
		
		return null;
	}
	
	public static IScope getContainingScope( IASTStatement statement ){
		IASTNode parent = statement.getParent();
		IScope scope = null;
		if( parent instanceof IASTCompoundStatement ){
		    IASTCompoundStatement compound = (IASTCompoundStatement) parent;
		    scope = compound.getScope();
		} else if( parent instanceof IASTStatement ){
			scope = getContainingScope( (IASTStatement)parent );
		} else if( parent instanceof IASTFunctionDefinition ){
			IASTFunctionDeclarator fnDeclarator = ((IASTFunctionDefinition) parent ).getDeclarator();
			IFunction function = (IFunction) fnDeclarator.getName().resolveBinding();
			scope = function.getFunctionScope();
		}
		
		if( statement instanceof IASTGotoStatement || statement instanceof IASTLabelStatement ){
		    //labels have function scope
		    while( scope != null && !(scope instanceof ICFunctionScope) ){
		        scope = scope.getParent();
		    }
		}
		
		return scope;
	}
	
	public static IScope getContainingScope( IASTDeclSpecifier compTypeSpec ){
	    IASTNode parent = compTypeSpec.getParent();
	    return getContainingScope( (IASTSimpleDeclaration) parent );
	}

	/**
	 * @param parameterDeclaration
	 * @return
	 */
	public static IScope getContainingScope(IASTParameterDeclaration parameterDeclaration) {
		IASTNode parent = parameterDeclaration.getParent();
		if( parent instanceof IASTFunctionDeclarator ){
			IASTFunctionDeclarator functionDeclarator = (IASTFunctionDeclarator) parent;
			IASTName fnName = functionDeclarator.getName();
			IFunction function = (IFunction) fnName.resolveBinding();
			return function.getFunctionScope();
		}
		
		return null;
	}
	
	public static IASTNode getContainingBlockItem( IASTNode node ){
		IASTNode parent = node.getParent();
		if( parent == null )
			return null;
		
		if( parent instanceof IASTDeclaration ){
			IASTNode p = parent.getParent();
			if( p instanceof IASTDeclarationStatement )
				return p;
			return parent;
		} else if( parent instanceof IASTExpression ){
			IASTNode p = parent.getParent();
			if( p instanceof IASTStatement )
				return p;
		} else if ( parent instanceof IASTStatement || parent instanceof IASTTranslationUnit ) {
			return parent;
		}
		
		return getContainingBlockItem( parent );
	}
	
	static private IBinding resolveBinding( IASTNode node ){
		if( node instanceof IASTIdExpression ){
			return CPPSemantics.resolveBinding( ((IASTIdExpression)node).getName() );
		} else if( node instanceof ICPPASTFieldReference ){
			return CPPSemantics.resolveBinding( ((ICPPASTFieldReference)node).getFieldName() );
		}
		return null;
	}
	
	public static abstract class CPPBaseVisitorAction {
		public boolean processNames          = false;
		public boolean processDeclarations   = false;
		public boolean processInitializers   = false;
		public boolean processParameterDeclarations = false;
		public boolean processDeclarators    = false;
		public boolean processDeclSpecifiers = false;
		public boolean processExpressions    = false;
		public boolean processStatements     = false;
		public boolean processTypeIds        = false;
		public boolean processEnumerators    = false;
		public boolean processBaseSpecifiers;
		
		/**
		 * @return true to continue visiting, return false to stop
		 */
		public boolean processName( IASTName name ) 					{ return true; }
		public boolean processDeclaration( IASTDeclaration declaration ){ return true; }
		public boolean processInitializer( IASTInitializer initializer ){ return true; }
		public boolean processParameterDeclaration( IASTParameterDeclaration parameterDeclaration ) { return true; }
		public boolean processDeclarator( IASTDeclarator declarator )   { return true; }
		public boolean processDeclSpecifier( IASTDeclSpecifier declSpec ){return true; }
		public boolean processExpression( IASTExpression expression )   { return true; }
		public boolean processStatement( IASTStatement statement )      { return true; }
		public boolean processTypeId( IASTTypeId typeId )               { return true; }
		public boolean processEnumerator( IASTEnumerator enumerator )   { return true; }
		public boolean processBaseSpecifier(ICPPASTBaseSpecifier specifier) { return true; }
	}
	
	public static void visitTranslationUnit( IASTTranslationUnit tu, CPPBaseVisitorAction action ){
		IASTDeclaration [] decls = tu.getDeclarations();
		for( int i = 0; i < decls.length; i++ ){
			if( !visitDeclaration( decls[i], action ) ) return;
		}
	}

	/**
	 * @param declaration
	 * @param action
	 * @return
	 */
	public static boolean visitDeclaration(IASTDeclaration declaration, CPPBaseVisitorAction action) {
		if( action.processDeclarations ) 
			if( !action.processDeclaration( declaration ) ) return false;
		
		if( declaration instanceof IASTSimpleDeclaration ){
			IASTSimpleDeclaration simple = (IASTSimpleDeclaration) declaration;
			if( !visitDeclSpecifier( simple.getDeclSpecifier(), action ) ) return false;
			IASTDeclarator [] dtors = simple.getDeclarators();
			for( int i = 0; i < dtors.length; i++ ){
				if( !visitDeclarator( dtors[i], action) ) return false;
			}
		} else if( declaration instanceof IASTFunctionDefinition ){
			IASTFunctionDefinition function = (IASTFunctionDefinition) declaration;
			if( !visitDeclSpecifier( function.getDeclSpecifier(), action ) ) return false;
			if( !visitDeclarator( function.getDeclarator(), action ) ) return false;
			if( !visitStatement( function.getBody(), action ) ) return false;
		} else if( declaration instanceof ICPPASTUsingDeclaration ){
			if( !visitName( ((ICPPASTUsingDeclaration)declaration).getName(), action ) ) return false;
		} else if( declaration instanceof ICPPASTUsingDirective ){
			if( !visitName( ((ICPPASTUsingDirective)declaration).getQualifiedName(), action ) ) return false;
		} else if( declaration instanceof ICPPASTNamespaceDefinition ){
			ICPPASTNamespaceDefinition namespace = (ICPPASTNamespaceDefinition) declaration;
			if( !visitName( namespace.getName(), action ) ) return false;
			IASTDeclaration [] decls = namespace.getDeclarations();
			for( int i = 0; i < decls.length; i++ ){
				if( !visitDeclaration( decls[i], action ) ) return false;
			}
		} else if( declaration instanceof ICPPASTNamespaceAlias ){
			ICPPASTNamespaceAlias alias = (ICPPASTNamespaceAlias) declaration;
			if( !visitName( alias.getQualifiedName(), action ) ) return false;
			if( !visitName( alias.getAlias(), action ) ) return false;
		} else if( declaration instanceof ICPPASTLinkageSpecification ){
			IASTDeclaration [] decls = ((ICPPASTLinkageSpecification) declaration).getDeclarations();
			for( int i = 0; i < decls.length; i++ ){
				if( !visitDeclaration( decls[i], action ) ) return false;
			}
		} else if( declaration instanceof ICPPASTTemplateDeclaration ){
			ICPPASTTemplateDeclaration template = (ICPPASTTemplateDeclaration) declaration;
			ICPPASTTemplateParameter [] params = template.getTemplateParameters();
			for( int i = 0; i < params.length; i++ ){
				if( !visitTemplateParameter( params[i], action ) ) return false;
			}
			if( !visitDeclaration( template.getDeclaration(), action ) ) return false;
		} else if( declaration instanceof ICPPASTTemplateSpecialization ){
			if( !visitDeclaration( ((ICPPASTTemplateSpecialization) declaration).getDeclaration(), action ) ) return false;
		}
		return true;
	}

	/**
	 * @param name
	 * @param action
	 * @return
	 */
	public static boolean visitName(IASTName name, CPPBaseVisitorAction action) {
		if( action.processNames )
			if( !action.processName( name ) ) return false;
			
		if( name instanceof ICPPASTQualifiedName ){
			IASTName [] names = ((ICPPASTQualifiedName)name).getNames();
			for( int i = 0; i < names.length; i++ ){
				if( !visitName( names[i], action ) ) return false;
			}
		}
		return true;
	}

	/**
	 * @param declSpecifier
	 * @param action
	 * @return
	 */
	public static boolean visitDeclSpecifier(IASTDeclSpecifier declSpecifier, CPPBaseVisitorAction action) {
		if( action.processDeclSpecifiers )
			if( !action.processDeclSpecifier( declSpecifier ) ) return false;
			
		if( declSpecifier instanceof ICPPASTCompositeTypeSpecifier ){
			ICPPASTCompositeTypeSpecifier composite = (ICPPASTCompositeTypeSpecifier) declSpecifier;
			if( !visitName( composite.getName(), action ) ) return false;
			ICPPASTBaseSpecifier [] bases = composite.getBaseSpecifiers();
			for( int i = 0; i < bases.length; i++ ) {
				if( !visitBaseSpecifier( bases[i], action ) ) return false;
			}
			IASTDeclaration [] decls = composite.getMembers();
			for( int i = 0; i < decls.length; i++ ){
				if( !visitDeclaration( decls[i], action ) ) return false;
			}
		} else if( declSpecifier instanceof ICPPASTElaboratedTypeSpecifier ){
			if( !visitName( ((ICPPASTElaboratedTypeSpecifier) declSpecifier).getName(), action ) ) return false;
		} else if( declSpecifier instanceof IASTEnumerationSpecifier ){
			IASTEnumerationSpecifier enum = (IASTEnumerationSpecifier) declSpecifier;
			if( !visitName( enum.getName(), action ) ) return false;
			IASTEnumerator [] etors = enum.getEnumerators();
			for( int i = 0; i < etors.length; i++ ){
				if( !visitEnumerator( etors[i], action ) ) return false;
			}
		} else if( declSpecifier instanceof ICPPASTNamedTypeSpecifier ){
			if( !visitName( ((ICPPASTNamedTypeSpecifier)declSpecifier).getName(), action ) ) return false;
		} else if( declSpecifier instanceof IGPPASTSimpleDeclSpecifier ) {
			IASTExpression typeOf = ((IGPPASTSimpleDeclSpecifier)declSpecifier).getTypeofExpression();
			if( typeOf != null )
				if( !visitExpression( typeOf, action ) ) return false;
		}
		return true;
	}

	/**
	 * @param declarator
	 * @param action
	 * @return
	 */
	public static boolean visitDeclarator(IASTDeclarator declarator, CPPBaseVisitorAction action) {
		if( action.processDeclarators )
			if( !action.processDeclarator( declarator ) ) return false;
			
		if( !visitName( declarator.getName(), action ) ) return false;
		
		if( declarator.getNestedDeclarator() != null )
			if( !visitDeclarator( declarator.getNestedDeclarator(), action ) ) return false;
		
		if( declarator instanceof ICPPASTFunctionDeclarator ){
			ICPPASTFunctionDeclarator fdtor = (ICPPASTFunctionDeclarator) declarator;
		    IASTParameterDeclaration [] list = fdtor.getParameters();
			for( int i = 0; i < list.length; i++ ){
				IASTParameterDeclaration param = list[i];
				if( !visitDeclSpecifier( param.getDeclSpecifier(), action ) ) return false;
				if( !visitDeclarator( param.getDeclarator(), action ) ) return false;
			}
			ICPPASTConstructorChainInitializer [] ctorChain = fdtor.getConstructorChain();
			for( int i = 0; i < ctorChain.length; i++ ){
				if( !visitName( ctorChain[i].getMemberInitializerId(), action ) ) return false;
				if( !visitExpression( ctorChain[i].getInitializerValue(), action ) ) return false;
			}
			IASTTypeId [] typeIds = fdtor.getExceptionSpecification();
			for( int i = 0; i < typeIds.length; i++ ){
				if( !visitTypeId( typeIds[i], action ) ) return false;
			}
			
			if( declarator instanceof ICPPASTFunctionTryBlockDeclarator ){
				ICPPASTCatchHandler [] catchHandlers = ((ICPPASTFunctionTryBlockDeclarator)declarator).getCatchHandlers();
				for( int i = 0; i < catchHandlers.length; i++ ){
					if( !visitStatement( catchHandlers[i], action ) ) return false;
				}
			}
			
		}
		
		if( declarator.getInitializer() != null )
		    if( !visitInitializer( declarator.getInitializer(), action ) ) return false;
		    
		return true;
	}
	
	/**
	 * @param body
	 * @param action
	 * @return
	 */
	public static boolean visitStatement(IASTStatement statement, CPPBaseVisitorAction action) {
		if( action.processStatements )
			if( !action.processStatement( statement ) ) return false;
			
		if( statement instanceof IASTCompoundStatement ){
			IASTStatement [] list = ((IASTCompoundStatement) statement).getStatements();
			for( int i = 0; i < list.length; i++ ){
			    if( list[i] == null ) break;
				if( !visitStatement( list[i], action ) ) return false;
			}
		} else if( statement instanceof IASTDeclarationStatement ){
			if( !visitDeclaration( ((IASTDeclarationStatement)statement).getDeclaration(), action ) ) return false;
		} else if( statement instanceof IASTExpressionStatement ){
		    if( !visitExpression( ((IASTExpressionStatement)statement).getExpression(), action ) ) return false;
		} else if( statement instanceof IASTCaseStatement ){
		    if( !visitExpression( ((IASTCaseStatement)statement).getExpression(), action ) ) return false;
		} else if( statement instanceof IASTDoStatement ){
		    if( !visitStatement( ((IASTDoStatement)statement).getBody(), action ) ) return false;
		    if( !visitExpression( ((IASTDoStatement)statement).getCondition(), action ) ) return false;
		} else if( statement instanceof IASTGotoStatement ){
		    if( !visitName( ((IASTGotoStatement)statement).getName(), action ) ) return false;
		} else if( statement instanceof IASTIfStatement ){
		    if( !visitExpression( ((IASTIfStatement) statement ).getCondition(), action ) ) return false;
		    if( !visitStatement( ((IASTIfStatement) statement ).getThenClause(), action ) ) return false;
		    if( !visitStatement( ((IASTIfStatement) statement ).getElseClause(), action ) ) return false;
		} else if( statement instanceof IASTLabelStatement ){
		    if( !visitName( ((IASTLabelStatement)statement).getName(), action ) ) return false;
		} else if( statement instanceof IASTReturnStatement ){
		    if( !visitExpression( ((IASTReturnStatement) statement ).getReturnValue(), action ) ) return false;
		} else if( statement instanceof IASTSwitchStatement ){
		    if( !visitExpression( ((IASTSwitchStatement) statement ).getController(), action ) ) return false;
		    if( !visitStatement( ((IASTSwitchStatement) statement ).getBody(), action ) ) return false;
		} else if( statement instanceof IASTWhileStatement ){
		    if( !visitExpression( ((IASTWhileStatement) statement ).getCondition(), action ) ) return false;
		    if( !visitStatement( ((IASTWhileStatement) statement ).getBody(), action ) ) return false;
		} else if( statement instanceof IASTForStatement ){
		    IASTForStatement s = (IASTForStatement) statement;
		    if( s.getInitDeclaration() != null )
		        if( !visitDeclaration( s.getInitDeclaration(), action ) ) return false;
		    if( s.getInitExpression() != null )
		        if( !visitExpression( s.getInitExpression(), action ) ) return false;
		    if( !visitExpression( s.getCondition(), action ) ) return false;
		    if( !visitExpression( s.getIterationExpression(), action ) ) return false;
		    if( !visitStatement( s.getBody(), action ) ) return false;
		} else if( statement instanceof ICPPASTCatchHandler ){
			if( !visitDeclaration( ((ICPPASTCatchHandler) statement).getDeclaration(), action ) ) return false;
			if( !visitStatement( ((ICPPASTCatchHandler) statement).getCatchBody(), action ) ) return false;
		} else if( statement instanceof ICPPASTTryBlockStatement ){
			if( !visitStatement( ((ICPPASTTryBlockStatement)statement).getTryBody(), action ) ) return false;
			ICPPASTCatchHandler [] handlers = ((ICPPASTTryBlockStatement)statement).getCatchHandlers();
			for( int i = 0; i < handlers.length; i++ ){
				if( !visitStatement( handlers[i], action ) ) return false;
			}
		}
		
		return true;
	}

	/**
	 * @param typeOf
	 * @param action
	 * @return
	 */
	public static boolean visitExpression(IASTExpression expression, CPPBaseVisitorAction action) {
		if( action.processExpressions )
			if( !action.processExpression( expression ) ) return false;
			
		if( expression instanceof IASTArraySubscriptExpression ){
		    if( !visitExpression( ((IASTArraySubscriptExpression)expression).getArrayExpression(), action ) ) return false;
		    if( !visitExpression( ((IASTArraySubscriptExpression)expression).getSubscriptExpression(), action ) ) return false;
		} else if( expression instanceof IASTBinaryExpression ){
		    if( !visitExpression( ((IASTBinaryExpression)expression).getOperand1(), action ) ) return false;
		    if( !visitExpression( ((IASTBinaryExpression)expression).getOperand2(), action ) ) return false;
		} else if( expression instanceof IASTConditionalExpression){
		    if( !visitExpression( ((IASTConditionalExpression)expression).getLogicalConditionExpression(), action ) ) return false;
		    if( !visitExpression( ((IASTConditionalExpression)expression).getNegativeResultExpression(), action ) ) return false;
		    if( !visitExpression( ((IASTConditionalExpression)expression).getPositiveResultExpression(), action ) ) return false;
		} else if( expression instanceof IASTExpressionList ){
			IASTExpression[] list = ((IASTExpressionList)expression).getExpressions();
			for( int i = 0; i < list.length; i++){
			    if( list[i] == null ) break;
			    if( !visitExpression( list[i], action ) ) return false;
			}
		} else if( expression instanceof IASTFieldReference ){
		    if( !visitExpression( ((IASTFieldReference)expression).getFieldOwner(), action ) ) return false;
		    if( !visitName( ((IASTFieldReference)expression).getFieldName(), action ) ) return false;
		} else if( expression instanceof IASTFunctionCallExpression ){
		    if( !visitExpression( ((IASTFunctionCallExpression)expression).getFunctionNameExpression(), action ) ) return false;
		    if( !visitExpression( ((IASTFunctionCallExpression)expression).getParameterExpression(), action ) ) return false;
		} else if( expression instanceof IASTIdExpression ){
		    if( !visitName( ((IASTIdExpression)expression).getName(), action ) ) return false;
		} else if( expression instanceof IASTTypeIdExpression ){
		    if( !visitTypeId( ((IASTTypeIdExpression)expression).getTypeId(), action ) ) return false;
		} else if( expression instanceof IASTCastExpression ){
		    if( !visitTypeId( ((IASTCastExpression)expression).getTypeId(), action ) ) return false;
		    if( !visitExpression( ((IASTCastExpression)expression).getOperand(), action ) ) return false;
		} else if( expression instanceof IASTUnaryExpression ){
		    if( !visitExpression( ((IASTUnaryExpression)expression).getOperand(), action ) ) return false;
		} else if( expression instanceof IGNUASTCompoundStatementExpression ){
		    if( !visitStatement( ((IGNUASTCompoundStatementExpression)expression).getCompoundStatement(), action ) ) return false;
		} else if( expression instanceof ICPPASTDeleteExpression ){
			if( !visitExpression( ((ICPPASTDeleteExpression)expression).getOperand(), action ) )  return false;
		} else if( expression instanceof ICPPASTNewExpression ) {
			ICPPASTNewExpression newExp = (ICPPASTNewExpression) expression;
			if( newExp.getNewPlacement() != null )
				if( !visitExpression( newExp.getNewPlacement(), action ) ) return false;
			if( newExp.getTypeId() != null )
				if( !visitTypeId( newExp.getTypeId(), action ) ) return false;
			IASTExpression [] exps = newExp.getNewTypeIdArrayExpressions();
			for( int i = 0; i < exps.length; i++ ){
				if( !visitExpression( exps[i], action ) ) return false;
			}
			if( newExp.getNewInitializer() != null )
				if( !visitExpression( newExp.getNewInitializer(), action ) ) return false;
		} else if( expression instanceof ICPPASTSimpleTypeConstructorExpression ){
			if( !visitExpression( ((ICPPASTSimpleTypeConstructorExpression)expression).getInitialValue(), action ) ) return false;
		} else if( expression instanceof ICPPASTTypenameExpression ){
			if( !visitName( ((ICPPASTTypenameExpression)expression).getName(), action ) ) return false;
			if( !visitExpression( ((ICPPASTTypenameExpression)expression).getInitialValue(), action ) )  return false;
		}
		return true;	
	}
	
	/**
	 * @param typeId
	 * @param action
	 * @return
	 */
	public static boolean visitTypeId(IASTTypeId typeId, CPPBaseVisitorAction action) {
		if( action.processTypeIds )
			if( !action.processTypeId( typeId ) ) return false;
		if( !visitDeclarator( typeId.getAbstractDeclarator(), action ) ) return false;
		if( !visitDeclSpecifier( typeId.getDeclSpecifier(), action ) ) return false;
		return true;
	}

	/**
	 * @param initializer
	 * @param action
	 * @return
	 */
	public static boolean visitInitializer(IASTInitializer initializer, CPPBaseVisitorAction action) {
		if( action.processInitializers )
			if( !action.processInitializer( initializer ) ) return false;
			
	    if( initializer instanceof IASTInitializerExpression ){
	        if( !visitExpression( ((IASTInitializerExpression) initializer).getExpression(), action ) ) return false;
	    } else if( initializer instanceof IASTInitializerList ){
	        IASTInitializer [] list = ((IASTInitializerList) initializer).getInitializers();
	        for( int i = 0; i < list.length; i++ ){
	            if( !visitInitializer( list[i], action ) ) return false;
	        }
	    } else if( initializer instanceof ICPPASTConstructorInitializer ){
	    	if( !visitExpression( ((ICPPASTConstructorInitializer) initializer).getExpression(), action ) ) return false;
	    }
	    return true;
	}

	/**
	 * @param enumerator
	 * @param action
	 * @return
	 */
	public static boolean visitEnumerator(IASTEnumerator enumerator, CPPBaseVisitorAction action) {
		if( action.processEnumerators )
			if( !action.processEnumerator( enumerator ) ) return false;
			
	    if( !visitName( enumerator.getName(), action ) ) return false;
	    if( enumerator.getValue() != null )
	        if( !visitExpression( enumerator.getValue(), action ) ) return false;
	    return true;
	}

	/**
	 * @param specifier
	 * @param action
	 * @return
	 */
	public static boolean visitBaseSpecifier(ICPPASTBaseSpecifier specifier, CPPBaseVisitorAction action) {
		if( action.processBaseSpecifiers )
			if( !action.processBaseSpecifier( specifier ) ) return false;
			
	    if( !visitName( specifier.getName(), action ) ) return false;
	    return true;
	}

	/**
	 * @param parameter
	 * @param action
	 * @return
	 */
	public static boolean visitTemplateParameter(ICPPASTTemplateParameter parameter, CPPBaseVisitorAction action) {
		return true;
	}

	public static IFunctionType createType( ICPPASTFunctionDeclarator fnDtor ){
	    List pTypes = Collections.EMPTY_LIST;
	    IASTParameterDeclaration [] params = fnDtor.getParameters();
	    IType pt = null;
	    
	    for( int i = 0; i < params.length; i++ ){
	        IASTDeclSpecifier pDeclSpec = params[i].getDeclSpecifier();
	        IASTDeclarator pDtor = params[i].getDeclarator();
	        //8.3.5-3 
	        //Any cv-qualifier modifying a parameter type is deleted.
	        //so only create the base type from the declspec and not the qualifiers
	        pt = getBaseType( pDeclSpec );
	        
	        pt = getPointerTypes( pt, pDtor );
	        
	        //any parameter of type array of T is adjusted to be pointer to T
	        if( pDtor instanceof IASTArrayDeclarator ){
	            IASTArrayModifier [] mods = ((IASTArrayDeclarator)pDtor).getArrayModifiers();
	    	    for( int j = 0; j < mods.length - 1; j++ ){
	    	        pt = new CPPArrayType( pt );
	    	    }
	    	    if( mods.length > 0 ){
	    	        pt = new CPPPointerType( pt );
	    	    }
	        }
	        
	        //any parameter to type function returning T is adjusted to be pointer to function
	        if( pt instanceof IFunctionType ){
	            pt = new CPPPointerType( pt );
	        }
	        
	        if( pTypes == Collections.EMPTY_LIST ){
	            pTypes = new ArrayList();
	        }
	        pTypes.add( pt );
	    }
	    
	    IASTNode node = fnDtor.getParent();
	    IASTDeclSpecifier declSpec = null;
	    if( node instanceof IASTSimpleDeclaration )
	        declSpec = ((IASTSimpleDeclaration)node).getDeclSpecifier();
	    else if ( node instanceof IASTFunctionDefinition )
	    	declSpec = ((IASTFunctionDefinition)node).getDeclSpecifier();
	    
	    IType returnType = createType( declSpec );
	    returnType = getPointerTypes( returnType, fnDtor );
	    
	    IType [] array = new IType [ pTypes.size() ];
	    return new CPPFunctionType( returnType, (IType[]) pTypes.toArray( array ) );
	}
	
	/**
	 * @param declarator
	 * @return
	 */
	public static IType createType(IASTDeclarator declarator) {
	    if( declarator instanceof ICPPASTFunctionDeclarator )
	        return createType( (ICPPASTFunctionDeclarator)declarator );
	    
		IASTNode parent = declarator.getParent();
		IASTDeclSpecifier declSpec = null;
		if( parent instanceof IASTParameterDeclaration )
			declSpec = ((IASTParameterDeclaration) parent).getDeclSpecifier();
		else if( parent instanceof IASTSimpleDeclaration )
			declSpec = ((IASTSimpleDeclaration)parent).getDeclSpecifier();
		
		IType type = createType( declSpec );
		
		type = getPointerTypes( type, declarator );
		if( declarator instanceof IASTArrayDeclarator )
		    type = getArrayTypes( type, (IASTArrayDeclarator) declarator );
		return type;
	}

	private static IType getPointerTypes( IType type, IASTDeclarator declarator ){
	    IASTPointerOperator [] ptrOps = declarator.getPointerOperators();
		for( int i = ptrOps.length - 1; i >= 0; i-- ){
			type = new CPPPointerType( type, (IASTPointer) ptrOps[i] );
		}
		return type;
	}
	private static IType getArrayTypes( IType type, IASTArrayDeclarator declarator ){
	    IASTArrayModifier [] mods = declarator.getArrayModifiers();
	    for( int i = 0; i < mods.length; i++ ){
	        type = new CPPArrayType( type );
	    }
	    return type;
	}
	
	/**
	 * @param declSpec
	 * @return
	 */
	protected static IType createType(IASTDeclSpecifier declSpec ) {
	    IType type = getBaseType( declSpec );
		
		if( type != null && ( declSpec.isConst() || declSpec.isVolatile() ) ){
		    type = new CPPQualifierType( type, declSpec.isConst(), declSpec.isVolatile() );
		}
		return type;
	}

	private static IType getBaseType( IASTDeclSpecifier declSpec ){
	    IType type = null;
	    if( declSpec instanceof ICPPASTCompositeTypeSpecifier ){
			IBinding binding = ((ICPPASTCompositeTypeSpecifier) declSpec).getName().resolveBinding();
			if( binding instanceof IType) 
				type = (IType) binding;
		} else if( declSpec instanceof ICPPASTElaboratedTypeSpecifier ){
			IBinding binding = ((ICPPASTElaboratedTypeSpecifier)declSpec).getName().resolveBinding();
			if( binding instanceof IType )
				type = (IType) binding;
		} else if( declSpec instanceof ICPPASTSimpleDeclSpecifier ){
			ICPPASTSimpleDeclSpecifier spec = (ICPPASTSimpleDeclSpecifier) declSpec;
			int bits = ( spec.isLong()     ? CPPBasicType.IS_LONG  : 0 ) &
					   ( spec.isShort()    ? CPPBasicType.IS_SHORT : 0 ) &
					   ( spec.isSigned()   ? CPPBasicType.IS_SIGNED: 0 ) &
					   ( spec.isUnsigned() ? CPPBasicType.IS_SHORT : 0 );
			if( spec instanceof IGPPASTSimpleDeclSpecifier ){
				IGPPASTSimpleDeclSpecifier gspec = (IGPPASTSimpleDeclSpecifier) spec;
				bits &= ( gspec.isLongLong() ? GPPBasicType.IS_LONGLONG : 0 );
				type = new GPPBasicType( spec.getType(), bits, getExpressionType(gspec.getTypeofExpression()) );
			} else {
			    type = new CPPBasicType( spec.getType(), bits );
			}
		}
		return type;
	}
	/**
	 * @param expression
	 * @return
	 */
	public static IType getExpressionType(IASTExpression expression) {
		if( expression == null )
			return null;
	    if( expression instanceof IASTIdExpression ){
	        IBinding binding = resolveBinding( expression );
			if( binding instanceof IVariable ){
				return ((IVariable)binding).getType();
			}
	    } else if( expression instanceof IASTCastExpression ){
	        IASTTypeId id = ((IASTCastExpression)expression).getTypeId();
	        return createType( id.getAbstractDeclarator() );
	    } else if( expression instanceof ICPPASTLiteralExpression ){
	    	switch( ((ICPPASTLiteralExpression) expression).getKind() ){
	    		case ICPPASTLiteralExpression.lk_this : break;
	    		case ICPPASTLiteralExpression.lk_true :
	    		case ICPPASTLiteralExpression.lk_false:
	    			return new CPPBasicType( ICPPBasicType.t_bool, 0 );
	    		case IASTLiteralExpression.lk_char_constant:
	    			return new CPPBasicType( IBasicType.t_char, 0 );
	    		case IASTLiteralExpression.lk_float_constant:
	    			return new CPPBasicType( IBasicType.t_float, 0 );
	    		case IASTLiteralExpression.lk_integer_constant:
	    			return new CPPBasicType( IBasicType.t_int, 0 );
	    		case IASTLiteralExpression.lk_string_literal:
	    			IType type = new CPPBasicType( IBasicType.t_char, 0 );
	    			return new CPPPointerType( type );
	    	}
	    	
	    }
	    return null;
	}
}
