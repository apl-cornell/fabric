package jif.translate;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public interface ToJavaExt extends Ext {
    NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException;
    Node toJava(JifToJavaRewriter rw) throws SemanticException;
}
