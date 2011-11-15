package jif.translate;

import jif.ast.DowngradeStmt;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public class DowngradeStmtToJavaExt_c extends ToJavaExt_c {
    public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
        DowngradeStmt n = (DowngradeStmt) node();
        return rw.bypass(n.bound()).bypass(n.label());
    }

    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        DowngradeStmt n = (DowngradeStmt) node();
        return n.body();
    }
}
